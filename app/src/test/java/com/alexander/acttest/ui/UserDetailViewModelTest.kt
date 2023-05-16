package com.alexander.acttest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.domain.GetRepositoriesByUserUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UserDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private val getRepositoriesByUserUseCase = mockk<GetRepositoriesByUserUseCase>(relaxed = true)

    private val dispatcher = UnconfinedTestDispatcher()

    private val viewModel = UserDetailViewModel(getRepositoriesByUserUseCase, dispatcher)

    private val observerGetRepositoryByUsername = mockk<Observer<ResponseStatus<List<RepositoryModel>>?>> { every { onChanged(any()) } just Runs }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel.repositoryListResponse.observeForever(observerGetRepositoryByUsername)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.repositoryListResponse.removeObserver(observerGetRepositoryByUsername)
    }

    @Test
    fun `when get repository list returns success should trigger sequence be Loading and Success status`() = runBlocking {
        val expectedResultLoading = ResponseStatus.Loading(true)
        val expectedResponseBody = listOf<RepositoryModel>()
        val expectedResultSuccess = ResponseStatus.Success(expectedResponseBody)

        coEvery {
            getRepositoriesByUserUseCase(any())
        } returns expectedResultSuccess

        viewModel.getRepositoriesByUser("")

        verifySequence {
            observerGetRepositoryByUsername.onChanged(expectedResultLoading)
            observerGetRepositoryByUsername.onChanged(expectedResultSuccess)
        }
    }

    @Test
    fun `when get repository list returns fail should trigger sequence be Loading and Failure Status`() = runBlocking {
        val expectedResultLoading = ResponseStatus.Loading(true)
        val expectedResultFailure = ResponseStatus.Failure(401, "")

        coEvery {
            getRepositoriesByUserUseCase(any())
        } returns expectedResultFailure

        viewModel.getRepositoriesByUser("")

        verifySequence {
            observerGetRepositoryByUsername.onChanged(expectedResultLoading)
            observerGetRepositoryByUsername.onChanged(expectedResultFailure)
        }
    }
}