package com.kaleksandra.featureprofile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaleksandra.featureprofile.domain.models.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private var _profileState = MutableStateFlow<Profile?>(null)
    val profileState: StateFlow<Profile?> = _profileState

    init {
        getProfile()
    }

    private fun getProfile() {
        //TODO: Add get profile function
        viewModelScope.launch {
            _profileState.emit(
                Profile(
                    "http://10.0.2.2:8080/api/collection/image/e9524da128a50166f2f0b35e2a8822fa.jpg",
                    "http://10.0.2.2:8080/api/collection/image/main_2x.jpg",
                    "Aleksandra10",
                    "a.testova@gmail.com"
                )
            )
        }
    }
}