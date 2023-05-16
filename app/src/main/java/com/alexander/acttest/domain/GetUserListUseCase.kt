package com.alexander.acttest.domain

import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.data.model.UserModel

interface GetUserListUseCase {
    suspend operator fun invoke(): ResponseStatus<List<UserModel>>
}