package me.hlatky.wbpo.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.hlatky.wbpo.repo.UserRepository
import me.hlatky.wbpo.ui.UIState
import me.hlatky.wbpo.ui.invokeAction

/** [ViewModel] for the [UserLoginFragment] that provides user login / register functionality. */
class UserLoginViewModel(
    private val repository: UserRepository
) : ViewModel() {

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
        val email = email.value.orEmpty()
        val password = password.value.orEmpty()
        val response = repository.login(email, password)
        // TODO Store access TOKEN
    }

    private suspend fun register() {
        val email = email.value.orEmpty()
        val password = password.value.orEmpty()
        val response = repository.register(email, password)
        // TODO Store User ID and access TOKEN
    }
}
