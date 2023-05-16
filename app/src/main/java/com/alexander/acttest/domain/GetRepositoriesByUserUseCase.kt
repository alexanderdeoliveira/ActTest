package com.alexander.acttest.domain

import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.data.model.ResponseStatus

interface GetRepositoriesByUserUseCase {
    suspend operator fun invoke(username: String): ResponseStatus<List<RepositoryModel>>
}