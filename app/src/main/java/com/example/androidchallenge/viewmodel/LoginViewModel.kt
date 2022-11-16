package com.example.androidchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.R
import com.example.androidchallenge.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthenticationRepository) : ViewModel() {

    val userName = MutableLiveData<String>()

    val password = MutableLiveData<String>()

    private val isUserNameValid: Boolean
        get() {
            val isNotEmpty = userName.value?.isNotEmpty() ?: false
            if (!isNotEmpty) postUserNameEmptyError()
            return isNotEmpty
        }

    private val isPasswordValid: Boolean
        get() {
            val isNotEmpty = password.value?.isNotEmpty() ?: false
            if (!isNotEmpty) postPasswordEmptyError()
            return isNotEmpty
        }

    private val isFieldsValid: Boolean
        get() = isUserNameValid.and(isPasswordValid)

    private val _userNameErrorMessageResId = MutableLiveData<Int>()
    val userNameErrorMessageResId: LiveData<Int>
        get() = _userNameErrorMessageResId

    private val _passwordErrorMessageResId = MutableLiveData<Int>()
    val passwordErrorMessageResId: LiveData<Int>
        get() = _passwordErrorMessageResId


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

    fun authenticate() = viewModelScope.launch {
        if (isFieldsValid) {
            _isLoading.postValue(true)
            try {
                val response = repository.authenticate(userName.value!!, password.value!!)
                if (response.isSuccessful)
                    _success.postValue(true)
                else {
                    if (response.errorBody() != null) {
                        _errorBody.postValue(response.errorBody())
                    }
                }
            } catch (e: Exception) {
                _error.postValue(e)
            }
        }
    }

    private fun postUserNameEmptyError() {
        _userNameErrorMessageResId.postValue(R.string.user_required)
    }


    private fun postPasswordEmptyError() {
        _passwordErrorMessageResId.postValue(R.string.password_required)
    }

}