package me.hlatky.wbpo.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import me.hlatky.wbpo.ui.UIState
import me.hlatky.wbpo.ui.invokeAction

/** [ViewModel] for the [LoginFragment] that provides user login / register functionality. */
class LoginViewModel : ViewModel() {

    /** Flag to switch between login / registration state. */
    val registrationNeeded = MutableLiveData(false)

    /** "Email" input value. */
    val email = MutableLiveData<String>()

    /** "Password" input value. */
    val password = MutableLiveData<String>()

    /** The result from [requestLogin].*/
    val loginResult = MutableLiveData<UIState<Boolean>>()

    /** The result from [requestRegister].*/
    val registerResult = MutableLiveData<UIState<Boolean>>()

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
        login()
    }

    private suspend fun login(): Boolean {
        // TODO Implement login
        delay(500)

        return true
    }
}
