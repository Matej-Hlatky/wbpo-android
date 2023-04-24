package me.hlatky.wbpo.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import me.hlatky.wbpo.ui.UIState
import me.hlatky.wbpo.ui.invokeAction
import java.io.IOException

/** [ViewModel] for the [UserLoginFragment] that provides user login / register functionality. */
class UserLoginViewModel : ViewModel() {

    /** Flag to switch between login / registration state. */
    val registrationNeeded = MutableLiveData(false)

    /** "Email" input value. */
    val email = MutableLiveData<String>()

    /** "Password" input value. */
    val password = MutableLiveData<String>()

    /** The result from [requestLogin].*/
    val loginResult = MutableLiveData<UIState<Unit>>()

    /** The result from [requestRegister].*/
    val registerResult = MutableLiveData<UIState<Unit>>()

    fun switchToRegistration() {
        registrationNeeded.value = true
    }

    fun switchToLogin() {
        registrationNeeded.value = false
    }

    fun requestLogin() = invokeAction(loginResult) {
        login()
    }

    fun requestRegister() = invokeAction(registerResult) {
        register()
    }

    private suspend fun login() {
        // TODO Implement login
        delay(150)
    }

    private suspend fun register() {
        // TODO Implement register
        delay(250)
        throw IOException("Some technical error")
    }
}
