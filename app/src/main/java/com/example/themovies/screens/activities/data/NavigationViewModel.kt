package com.example.themovies.screens.activities.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.screens.registration.AuthenticationRepository
import com.example.themovies.screens.registration.data.Session
import com.example.themovies.screens.registration.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    val user = MutableLiveData<User?>()

    fun getDetails(sessionId: String?) {
        viewModelScope.launch {
            user.value = authenticationRepository.getDetailsAboutAccount(sessionId)
        }
    }
}