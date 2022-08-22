package bruhcollective.itaysonlab.microapp.auth.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bruhcollective.itaysonlab.jetisoft.controllers.UbiSessionController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AuthScreenViewModel @Inject constructor(
    private val ubiSessionController: UbiSessionController
) : ViewModel() {
    var isAuthInProgress by mutableStateOf(false)
    var isEmailError by mutableStateOf(false)
    var isPasswordError by mutableStateOf(false)

    fun auth(username: String, password: String, onSuccess: () -> Unit) {
        isEmailError = false
        isPasswordError = false

        if (username.isEmpty()) {
            isEmailError = true
        }

        if (password.isEmpty()) {
            isPasswordError = true
        }

        if (isEmailError || isPasswordError) return

        viewModelScope.launch {
            isAuthInProgress = true

            when (ubiSessionController.createSession(
                UbiSessionController.SessionType.EmailPassword(
                email = username, password = password, rememberMe = true
            ))) {
                UbiSessionController.SessionResult.InvalidInfo -> {
                    isEmailError = true
                    isPasswordError = true
                }

                is UbiSessionController.SessionResult.Success -> onSuccess()
            }

            isAuthInProgress = false
        }
    }
}