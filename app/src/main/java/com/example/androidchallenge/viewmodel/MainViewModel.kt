package com.example.androidchallenge.viewmodel

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.Constants
import com.example.androidchallenge.model.TeamEntity
import com.example.androidchallenge.model.UserEntity
import com.example.androidchallenge.repository.TeamRepository
import com.example.androidchallenge.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val teamRepository: TeamRepository
    ) : ViewModel() {

    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity>
        get() = _user

    private val _teams = MutableLiveData<List<TeamEntity>>()
    val teams: LiveData<List<TeamEntity>>
        get() = _teams

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception>
        get() = _error

    init {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val response = userRepository.getUsers(stringPreferencesKey(Constants.TOKEN_KEY).name)
                if (response.isSuccessful && response.body() != null) {
                    _user.postValue(response.body())
                    _isLoading.postValue(false)
                }
            } catch (e: Exception){
                _error.postValue(e)
            }

            try {
                val response = teamRepository.getTeams()
                if (response.isSuccessful && response.body() != null) {
                    _teams.postValue(response.body())
                    _isLoading.postValue(false)
                }
            } catch (e: Exception){
                _error.postValue(e)
            }
        }

    }


}