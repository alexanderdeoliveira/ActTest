package com.alexander.acttest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexander.acttest.data.model.RepositoryModel
import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.util.SingleLiveEvent
import com.alexander.acttest.domain.GetRepositoriesByUserUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val getRepositoriesByUserUseCase: GetRepositoriesByUserUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _repositoryListResponse = SingleLiveEvent<ResponseStatus<List<RepositoryModel>>?>()
    val repositoryListResponse get() = _repositoryListResponse

    fun getRepositoriesByUser(username: String) {
        viewModelScope.launch(dispatcher) {
            _repositoryListResponse.postValue(ResponseStatus.Loading(true))
            _repositoryListResponse.postValue(getRepositoriesByUserUseCase(username))
        }
    }
}