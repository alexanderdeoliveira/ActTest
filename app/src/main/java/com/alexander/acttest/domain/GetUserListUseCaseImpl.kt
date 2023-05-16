package com.alexander.acttest.domain

import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.data.model.UserModel
import com.alexander.acttest.data.repository.UserRepository

class GetUserListUseCaseImpl(
    private val userRepository: UserRepository
): GetUserListUseCase {
    override suspend fun invoke(): ResponseStatus<List<UserModel>> {
        return userRepository.getUserList()
    }
}