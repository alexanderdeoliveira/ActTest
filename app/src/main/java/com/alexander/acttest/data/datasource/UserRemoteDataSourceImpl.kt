package com.alexander.acttest.data.datasource

import com.alexander.acttest.UserClient
import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.data.model.UserModel
import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.data.model.parseResponse

class UserRemoteDataSourceImpl(
    private val userClient: UserClient
): UserRemoteDataSource {
    override suspend fun getUserList(): ResponseStatus<List<UserModel>> {
        return userClient.getUserList().parseResponse()
    }

    override suspend fun getRepositoryByUser(username: String): ResponseStatus<List<RepositoryModel>> {
        return userClient.getRepositoriesByUser(username).parseResponse()
    }

    override suspend fun getUserByUsername(username: String): ResponseStatus<UserModel> {
        return userClient.getUserByUsername(username).parseResponse()
    }
}