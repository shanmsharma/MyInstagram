package com.sharma.user.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharma.user.domain.usecase.GetUserProfileUseCase
import com.sharma.user.domain.usecase.SaveUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val saveUserProfileUseCase: SaveUserProfileUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow<UserProfileState>(UserProfileState.Idle)
    val profileState: StateFlow<UserProfileState> = _profileState

    fun saveUserProfile(username: String, bio: String, profilePicUri: Uri?) {
        viewModelScope.launch {
            _profileState.value = UserProfileState.Loading
            val success = saveUserProfileUseCase.invoke(username, bio, profilePicUri)
            _profileState.value = if (success) UserProfileState.Success else UserProfileState.Error("Failed to save profile")
        }
    }

    fun fetchUserProfile() {
        viewModelScope.launch {
            _profileState.value = UserProfileState.Loading
            val data = getUserProfileUseCase.invoke()
            _profileState.value = if (data != null) UserProfileState.Data(data) else UserProfileState.Error("User not found")
        }
    }
}

sealed class UserProfileState {
    object Idle : UserProfileState()
    object Loading : UserProfileState()
    object Success : UserProfileState()
    data class Data(val userProfile: Map<String, Any>) : UserProfileState()
    data class Error(val message: String) : UserProfileState()
}
