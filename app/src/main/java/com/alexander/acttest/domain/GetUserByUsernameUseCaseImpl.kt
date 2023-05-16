package com.alexander.acttest.domain

import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.data.model.UserModel
import com.alexander.acttest.data.repository.UserRepository

class GetUserByUsernameUseCaseImpl(
    private val userRepository: UserRepository
): GetUserByUsernameUseCase {
    override suspend fun invoke(username: String): ResponseStatus<UserModel> {
        return userRepository.getUserByUsername(username)
    }
}