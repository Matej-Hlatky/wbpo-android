package me.hlatky.wbpo.ui.user.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.hlatky.wbpo.repo.UserRepository
import me.hlatky.wbpo.ui.UIState
import me.hlatky.wbpo.ui.invokeAction
import javax.inject.Inject

/** [ViewModel] for the [UserLoginFragment] that provides user login / register functionality. */
@HiltViewModel
class UserLoginViewModel @Inject constructor(
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

    /** Fills inputs with hardcoded values. */
    fun prefillInputs() {
        email.value = "eve.holt@reqres.in"
        password.value = "pistol"
    }

    private suspend fun login() {
        val email = email.value.orEmpty()
        val password = password.value.orEmpty()

        repository.login(email, password)
    }

    private suspend fun register() {
        val email = email.value.orEmpty()
        val password = password.value.orEmpty()

        repository.register(email, password)
    }
}
