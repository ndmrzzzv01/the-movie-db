package com.example.themovies.screens.registration.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themovies.screens.registration.AuthenticationRepository
import com.example.themovies.screens.registration.data.Token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    val token = MutableLiveData<Token?>()

    fun getRequestToken() {
        viewModelScope.launch {
            token.value = authenticationRepository.getRequestToken()
        }
    }

}