package com.alexander.acttest.data.repository

import com.alexander.acttest.data.datasource.UserRemoteDataSourceImpl
import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.data.model.UserModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    @MockK
    private val userRemoteDatasource = mockk<UserRemoteDataSourceImpl>()

    @MockK
    private val userRepository = UserRepositoryImpl(userRemoteDatasource)

    @MockK
    private val user = mockk<UserModel>()

    @Test
    fun `when get user list then it should return response success`() = runBlocking {
        val expectedResponseBody = listOf<UserModel>()
        val expectedReturn = ResponseStatus.Success(expectedResponseBody)

        coEvery { userRemoteDatasource.getUserList() } returns expectedReturn

        val result = userRepository.getUserList()

        assertEquals(ResponseStatus.Success(expectedResponseBody), result)
    }

    @Test
    fun `when get user list then it should return error exception`() = runBlocking {
        val expectedReturn = ResponseStatus.Failure(
            401,
            "Error"
        )
        coEvery { userRemoteDatasource.getUserList() } returns expectedReturn

        val result = userRepository.getUserList()

        assertEquals(expectedReturn, result)
    }

    @Test
    fun `when get user by username then it should return response success`() = runBlocking {
        val expectedReturn = ResponseStatus.Success(user)

        coEvery { userRemoteDatasource.getUserByUsername(any()) } returns expectedReturn

        val result = userRepository.getUserByUsername("")

        assertEquals(ResponseStatus.Success(user), result)
    }

    @Test
    fun `when get user by username then it should return error exception`() = runBlocking {
        val expectedReturn = ResponseStatus.Failure(
            401,
            "Error"
        )
        coEvery { userRemoteDatasource.getRepositoryByUser(any()) } returns expectedReturn

        val result = userRepository.getRepositoriesByUser("")

        assertEquals(expectedReturn, result)
    }

    @Test
    fun `when get repositories by username then it should return response success`() = runBlocking {
        val expectedResponseBody = listOf<RepositoryModel>()
        val expectedReturn = ResponseStatus.Success(expectedResponseBody)

        coEvery { userRemoteDatasource.getRepositoryByUser(any()) } returns expectedReturn

        val result = userRepository.getRepositoriesByUser("")

        assertEquals(ResponseStatus.Success(expectedResponseBody), result)
    }

    @Test
    fun `when get repositories by username then it should return error exception`() = runBlocking {
        val expectedReturn = ResponseStatus.Failure(
            401,
            "Error"
        )
        coEvery { userRemoteDatasource.getRepositoryByUser(any()) } returns expectedReturn

        val result = userRepository.getRepositoriesByUser("")

        assertEquals(expectedReturn, result)
    }
}