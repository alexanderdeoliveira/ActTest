package com.alexander.acttest.ui

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexander.acttest.data.model.ResponseStatus
import com.alexander.acttest.util.SingleLiveEvent
import com.alexander.acttest.data.model.UserModel
import com.alexander.acttest.domain.GetUserByUsernameUseCase
import com.alexander.acttest.domain.GetUserListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListViewModel(
    private val getUserListUseCase: GetUserListUseCase,
    private val getUserByUsernameUseCase: GetUserByUsernameUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _userListResponse = SingleLiveEvent<ResponseStatus<List<UserModel>>?>()
    val userListResponse get() = _userListResponse

    private val _userFetchedResponse = SingleLiveEvent<ResponseStatus<UserModel>?>()
    val userFetchedResponse get() = _userFetchedResponse

    private val _searchButtonEnabled = SingleLiveEvent<Boolean>()
    val searchButtonEnabled get() = _searchButtonEnabled

    fun getUsers() {
        viewModelScope.launch(dispatcher) {
            _userListResponse.postValue(ResponseStatus.Loading(true))
            _userListResponse.postValue(getUserListUseCase())
        }
    }

    fun getUserByUsername(username: String) {
        viewModelScope.launch(dispatcher) {
            _userFetchedResponse.postValue(ResponseStatus.Loading(true))
            _userFetchedResponse.postValue(getUserByUsernameUseCase(username))
        }
    }
}