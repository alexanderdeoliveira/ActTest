package com.alexander.acttest

import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.data.model.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserClient {

    @GET("users")
    suspend fun getUserList(): Response<List<UserModel>>

    @GET("users/{username}/repos")
    suspend fun getRepositoriesByUser(@Path("username") username: String): Response<List<RepositoryModel>>

    @GET("users/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): Response<UserModel>
}