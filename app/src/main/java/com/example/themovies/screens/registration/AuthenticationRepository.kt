package com.example.themovies.screens.registration

import com.example.themovies.api.AuthenticationApi
import com.example.themovies.screens.registration.data.DeletedSession
import com.example.themovies.screens.registration.data.Session
import com.example.themovies.screens.registration.data.Token
import com.example.themovies.screens.registration.data.User
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class AuthenticationRepository @Inject constructor(
    private val authenticationApi: AuthenticationApi
) {

    suspend fun getRequestToken(): Token? = withContext(Dispatchers.IO) {
        authenticationApi.getRequestToken()
    }

    suspend fun createSession(token: String): Session? = withContext(Dispatchers.IO) {
        authenticationApi.createSession(token)
    }

    suspend fun getDetailsAboutAccount(sessionId: String?): User = withContext(Dispatchers.IO) {
        authenticationApi.getDetailsAboutAccount(sessionId)
    }

    suspend fun deleteSession(sessionId: String): DeletedSession = withContext(Dispatchers.IO) {
        authenticationApi.deleteSession(sessionId)
    }
}