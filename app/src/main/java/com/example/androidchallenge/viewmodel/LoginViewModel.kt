package com.example.androidchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthenticationRepository) : ViewModel() {

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean>
        get() = _success

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception>
        get() = _error


    private val _errorBody = MutableLiveData<ResponseBody?>()
    val errorBody: LiveData<ResponseBody?>
        get() = _errorBody

    fun authenticate(userName: String, password: String) = viewModelScope.launch {
        _isLoading.postValue(true)
        try {
            val response = repository.authenticate(userName, password)
            if (response.isSuccessful)
                _success.postValue(true)
            else {
                if (response.errorBody() != null) {
                    _errorBody.postValue(response.errorBody())
                }
            }
        } catch (e: Exception){
             _error.postValue(e)
        }
    }

}