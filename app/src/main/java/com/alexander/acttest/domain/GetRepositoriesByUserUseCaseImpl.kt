package com.alexander.acttest.domain

import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.data.repository.UserRepository

class GetRepositoriesByUserUseCaseImpl(
    private val userRepository: UserRepository
): GetRepositoriesByUserUseCase {
    override suspend fun invoke(username: String): ResponseStatus<List<RepositoryModel>> {
        return userRepository.getRepositoriesByUser(username)
    }
}