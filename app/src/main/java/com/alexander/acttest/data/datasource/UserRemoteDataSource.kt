package com.alexander.acttest.data.datasource

import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.data.model.UserModel
import com.alexander.acttest.data.model.ResponseStatus

interface UserRemoteDataSource {
    suspend fun getUserList(): ResponseStatus<List<UserModel>>
    suspend fun getRepositoryByUser(username: String): ResponseStatus<List<RepositoryModel>>
    suspend fun getUserByUsername(username: String): ResponseStatus<UserModel>
}