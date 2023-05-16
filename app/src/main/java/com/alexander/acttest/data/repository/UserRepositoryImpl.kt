package com.alexander.acttest.data.repository

import com.alexander.acttest.data.datasource.UserRemoteDataSource
import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.data.model.UserModel
import com.alexander.acttest.data.model.ResponseStatus

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
): UserRepository {
    override suspend fun getUserList(): ResponseStatus<List<UserModel>> {
        return userRemoteDataSource.getUserList()
    }

    override suspend fun getRepositoriesByUser(username: String): ResponseStatus<List<RepositoryModel>> {
        return userRemoteDataSource.getRepositoryByUser(username)
    }

    override suspend fun getUserByUsername(username: String): ResponseStatus<UserModel> {
        return userRemoteDataSource.getUserByUsername(username)
    }
}