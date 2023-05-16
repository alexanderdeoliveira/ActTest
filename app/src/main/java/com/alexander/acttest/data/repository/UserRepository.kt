package com.alexander.acttest.data.repository

import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.data.model.UserModel
import com.alexander.acttest.data.model.ResponseStatus

interface UserRepository {
    suspend fun getUserList(): ResponseStatus<List<UserModel>>
    suspend fun getRepositoriesByUser(username: String): ResponseStatus<List<RepositoryModel>>
    suspend fun getUserByUsername(username: String): ResponseStatus<UserModel>
}