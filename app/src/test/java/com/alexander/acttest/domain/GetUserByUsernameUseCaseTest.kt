package com.alexander.acttest.domain

import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.data.model.UserModel
import com.alexander.acttest.data.repository.UserRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class GetUserByUsernameUseCaseTest {
    @MockK
    private val repositoryMock = mockk<UserRepository>()

    private val getUserByUsernameUseCase = GetUserByUsernameUseCaseImpl(repositoryMock)

    @MockK
    private val user = mockk<UserModel>()

    @Test
    fun `when get user by username then it should return response success`() = runBlocking {
        val expectedReturn = ResponseStatus.Success(user)

        coEvery { repositoryMock.getUserByUsername(any()) } returns expectedReturn

        assertEquals(ResponseStatus.Success(user), getUserByUsernameUseCase(""))
    }

    @Test
    fun `when get user by username then it should return error exception`() = runBlocking {
        val expectedReturn = ResponseStatus.Failure(
            401,
            "Error"
        )
        coEvery { repositoryMock.getUserByUsername(any()) } returns expectedReturn

        assertEquals(expectedReturn, getUserByUsernameUseCase(""))
    }
}