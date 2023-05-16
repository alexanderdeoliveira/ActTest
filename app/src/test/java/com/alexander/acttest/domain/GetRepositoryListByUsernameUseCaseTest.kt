package com.alexander.acttest.domain

import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.data.repository.UserRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class GetRepositoryListByUsernameUseCaseTest {
    @MockK
    private val repositoryMock = mockk<UserRepository>()

    private val getRepositoriesByUserUseCase = GetRepositoriesByUserUseCaseImpl(repositoryMock)

    @Test
    fun `when get user list then it should return response success`() = runBlocking {
        val expectedResponseBody = listOf<RepositoryModel>()
        val expectedReturn = ResponseStatus.Success(expectedResponseBody)

        coEvery { repositoryMock.getRepositoriesByUser(any()) } returns expectedReturn

        assertEquals(ResponseStatus.Success(expectedResponseBody), getRepositoriesByUserUseCase(""))
    }

    @Test
    fun `when get user list then it should return error exception`() = runBlocking {
        val expectedReturn = ResponseStatus.Failure(
            401,
            "Error"
        )
        coEvery { repositoryMock.getRepositoriesByUser(any()) } returns expectedReturn

        assertEquals(expectedReturn, getRepositoriesByUserUseCase(""))
    }
}