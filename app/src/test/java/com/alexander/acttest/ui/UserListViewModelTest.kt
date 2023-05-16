package com.alexander.acttest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.data.model.UserModel
import com.alexander.acttest.domain.GetUserByUsernameUseCase
import com.alexander.acttest.domain.GetUserListUseCase
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
class UserListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private val getUserListUseCase = mockk<GetUserListUseCase>(relaxed = true)

    @MockK
    private val getUserByUsernameUseCase = mockk<GetUserByUsernameUseCase>(relaxed = true)

    @MockK
    private val user = mockk<UserModel>()

    private val dispatcher = UnconfinedTestDispatcher()

    private val viewModel = UserListViewModel(getUserListUseCase, getUserByUsernameUseCase, dispatcher)

    private val observerGetUserList = mockk<Observer<ResponseStatus<List<UserModel>>?>> { every { onChanged(any()) } just Runs }

    private val observerGetUserByUsername = mockk<Observer<ResponseStatus<UserModel>?>> { every { onChanged(any()) } just Runs }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel.userListResponse.observeForever(observerGetUserList)
        viewModel.userFetchedResponse.observeForever(observerGetUserByUsername)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.userListResponse.removeObserver(observerGetUserList)
        viewModel.userFetchedResponse.removeObserver(observerGetUserByUsername)
    }

    @Test
    fun `when get user list returns success should trigger sequence be Loading and Success status`() = runBlocking {
        val expectedResultLoading = ResponseStatus.Loading(true)
        val expectedResponseBody = listOf<UserModel>()
        val expectedResultSuccess = ResponseStatus.Success(expectedResponseBody)

        coEvery {
            getUserListUseCase()
        } returns expectedResultSuccess

        viewModel.getUsers()

        verifySequence {
            observerGetUserList.onChanged(expectedResultLoading)
            observerGetUserList.onChanged(expectedResultSuccess)
        }
    }

    @Test
    fun `when get user list returns fail should trigger sequence be Loading and Failure Status`() = runBlocking {
        val expectedResultLoading = ResponseStatus.Loading(true)
        val expectedResultFailure = ResponseStatus.Failure(401, "")

        coEvery {
            getUserListUseCase()
        } returns expectedResultFailure

        viewModel.getUsers()

        verifySequence {
            observerGetUserList.onChanged(expectedResultLoading)
            observerGetUserList.onChanged(expectedResultFailure)
        }
    }

    @Test
    fun `when get user by username returns success should trigger sequence be Loading and Success status`() = runBlocking {
        val expectedResultLoading = ResponseStatus.Loading(true)
        val expectedResultSuccess = ResponseStatus.Success(user)

        coEvery {
            getUserByUsernameUseCase(any())
        } returns expectedResultSuccess

        viewModel.getUserByUsername("")

        verifySequence {
            observerGetUserByUsername.onChanged(expectedResultLoading)
            observerGetUserByUsername.onChanged(expectedResultSuccess)
        }
    }

    @Test
    fun `when get user by username returns fail should trigger sequence be Loading and Failure Status`() = runBlocking {
        val expectedResultLoading = ResponseStatus.Loading(true)
        val expectedResultFailure = ResponseStatus.Failure(401, "")

        coEvery {
            getUserByUsernameUseCase("")
        } returns expectedResultFailure

        viewModel.getUserByUsername("")

        verifySequence {
            observerGetUserByUsername.onChanged(expectedResultLoading)
            observerGetUserByUsername.onChanged(expectedResultFailure)
        }
    }
}