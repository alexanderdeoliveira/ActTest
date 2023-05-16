package com.alexander.acttest.domain

import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.data.model.UserModel

interface GetUserByUsernameUseCase {
    suspend operator fun invoke(username: String): ResponseStatus<UserModel>
}