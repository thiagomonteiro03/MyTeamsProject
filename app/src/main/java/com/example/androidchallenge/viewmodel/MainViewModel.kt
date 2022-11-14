package com.example.androidchallenge.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.model.TeamEntity
import com.example.androidchallenge.repository.TeamRepository
import com.example.androidchallenge.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val userRepository: UserRepository,
    val teamRepository: TeamRepository,
    val dataStore: DataStore<Preferences>
    ) : ViewModel() {

    private val _uiStateMain: MutableStateFlow<MainUiState> = MutableStateFlow(
        MainUiState.Empty
    )
    val uiStateMain: StateFlow<MainUiState> get() = _uiStateMain

    init {
        viewModelScope.launch {
            val response = teamRepository.getTeams()
            if (response.isSuccessful && response.body() != null) _uiStateMain.emit(MainUiState.Success(response.body()!!))
        }

    }

    sealed class MainUiState {
        object Empty : MainUiState()
        object Loading : MainUiState()
        object Error: MainUiState()
        data class Success(val teams: List<TeamEntity>) : MainUiState()
    }


}