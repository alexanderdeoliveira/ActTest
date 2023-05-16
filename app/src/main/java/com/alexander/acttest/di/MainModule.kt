package com.alexander.acttest.di

import com.alexander.acttest.data.datasource.UserRemoteDataSource
import com.alexander.acttest.data.datasource.UserRemoteDataSourceImpl
import com.alexander.acttest.data.repository.UserRepository
import com.alexander.acttest.data.repository.UserRepositoryImpl
import com.alexander.acttest.domain.GetRepositoriesByUserUseCase
import com.alexander.acttest.domain.GetRepositoriesByUserUseCaseImpl
import com.alexander.acttest.domain.GetUserByUsernameUseCase
import com.alexander.acttest.domain.GetUserByUsernameUseCaseImpl
import com.alexander.acttest.domain.GetUserListUseCase
import com.alexander.acttest.domain.GetUserListUseCaseImpl
import com.alexander.acttest.ui.UserDetailViewModel
import com.alexander.acttest.ui.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { UserListViewModel(get(), get()) }
    viewModel { UserDetailViewModel(get()) }
    single { NetworkModule.userClient }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<GetUserListUseCase> { GetUserListUseCaseImpl(get()) }
    single<GetRepositoriesByUserUseCase> { GetRepositoriesByUserUseCaseImpl(get()) }
    single<GetUserByUsernameUseCase> { GetUserByUsernameUseCaseImpl(get()) }
    single<UserRemoteDataSource> { UserRemoteDataSourceImpl(get()) }
}
