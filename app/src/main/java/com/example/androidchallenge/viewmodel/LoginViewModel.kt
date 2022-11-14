package com.example.androidchallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthenticationRepository) : ViewModel() {

    private val _uiStateAuth: MutableStateFlow<AuthenticationUiState> = MutableStateFlow(
        AuthenticationUiState.Empty
    )
    val uiStateAuth: StateFlow<AuthenticationUiState> get() = _uiStateAuth

    fun authenticate(userName: String, password: String) = viewModelScope.launch {
        _uiStateAuth.emit(AuthenticationUiState.Loading)
        val response = repository.authenticate(userName, password)
        if (response.isSuccessful && response.body() != null)
            _uiStateAuth.emit(AuthenticationUiState.Success)
        else {
            if (response.errorBody() != null) {
                _uiStateAuth.emit(AuthenticationUiState.Error)
            }
        }
    }

    sealed class AuthenticationUiState {
        object Empty : AuthenticationUiState()
        object Loading : AuthenticationUiState()
        object Error: AuthenticationUiState()
        object Success : AuthenticationUiState()
    }

}