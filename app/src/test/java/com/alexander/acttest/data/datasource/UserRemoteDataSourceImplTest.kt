package com.alexander.acttest.data.datasource

import com.alexander.acttest.UserClient
import com.alexander.acttest.data.datasource.UserRemoteDataSourceImpl
import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.data.model.UserModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class UserRemoteDataSourceImplTest {

    @MockK
    private val userClient = mockk<UserClient>()

    @MockK
    private val userRemoteDataSource = UserRemoteDataSourceImpl(userClient)

    @MockK
    private val user = mockk<UserModel>()

    @Test
    fun `when get user list then it should return response success`() = runBlocking {
        val expectedResponseBody = listOf<UserModel>()
        val expectedReturn = Response.success(expectedResponseBody)

        coEvery { userClient.getUserList() } returns expectedReturn

        val result = userRemoteDataSource.getUserList()

        assertEquals(ResponseStatus.Success(expectedResponseBody), result)
    }

    @Test
    fun `when get user list then it should return error exception`() = runBlocking {
        coEvery { userClient.getUserList() } returns Response.error(
            401,
            "{\"key\":[\"Unauthorized\"]}".toResponseBody("application/json".toMediaTypeOrNull())
        )

        val result = userRemoteDataSource.getUserList()

        assertEquals(ResponseStatus.Failure(401, "Response.error()"), result)
    }

    @Test
    fun `when get user by username then it should return response success`() = runBlocking {
        val expectedReturn = Response.success(user)

        coEvery { userClient.getUserByUsername(any()) } returns expectedReturn

        val result = userRemoteDataSource.getUserByUsername("")

        assertEquals(ResponseStatus.Success(user), result)
    }

    @Test
    fun `when get user by username then it should return error exception`() = runBlocking {
        coEvery { userClient.getUserByUsername(any()) } returns Response.error(
            401,
            "{\"key\":[\"Unauthorized\"]}".toResponseBody("application/json".toMediaTypeOrNull())
        )

        val result = userRemoteDataSource.getUserByUsername("")

        assertEquals(ResponseStatus.Failure(401, "Response.error()"), result)
    }

    @Test
    fun `when get repositories by username then it should return response success`() = runBlocking {
        val expectedResponseBody = listOf<RepositoryModel>()
        val expectedReturn = Response.success(expectedResponseBody)

        coEvery { userClient.getRepositoriesByUser(any()) } returns expectedReturn

        val result = userRemoteDataSource.getRepositoryByUser("")

        assertEquals(ResponseStatus.Success(expectedResponseBody), result)
    }

    @Test
    fun `when get repositories by username then it should return error exception`() = runBlocking {
        coEvery { userClient.getRepositoriesByUser(any()) } returns Response.error(
            401,
            "{\"key\":[\"Unauthorized\"]}".toResponseBody("application/json".toMediaTypeOrNull())
        )

        val result = userRemoteDataSource.getRepositoryByUser("")

        assertEquals(ResponseStatus.Failure(401, "Response.error()"), result)
    }
}