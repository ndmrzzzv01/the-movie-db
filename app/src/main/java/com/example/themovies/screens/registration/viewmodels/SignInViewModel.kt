package com.example.themovies.screens.registration.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.screens.registration.AuthenticationRepository
import com.example.themovies.screens.registration.data.Session
import com.example.themovies.screens.registration.data.Token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    val tokenValue = MutableLiveData<Token?>()
    val session = MutableLiveData<Session?>()

    fun getRequestToken() {
        viewModelScope.launch {
            tokenValue.value = authenticationRepository.getRequestToken()
        }
    }

    fun createSession() {
        viewModelScope.launch {
            tokenValue.value?.let {
                if (it.success == true) {
                    session.value = authenticationRepository.createSession(it.token ?: "")
                }
            }
        }
    }
}