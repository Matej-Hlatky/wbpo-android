package me.hlatky.wbpo.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/** [ViewModel] for the [LoginFragment] that provides user login / register functionality. */
class LoginViewModel : ViewModel() {

    /** Flag to switch between login / registration state. */
    val registrationNeeded = MutableLiveData(false)

    /** "Email" input value. */
    val email = MutableLiveData<String>()

    /** "Password" input value. */
    val password = MutableLiveData<String>()

    fun switchToRegistration() {
        registrationNeeded.value = true
    }

    fun switchToLogin() {
        registrationNeeded.value = false
    }

    fun requestLogin() {
        // TODO Implement requestLogin
        //viewModelScope.launch {  }
    }

    fun requestRegister() {
        // TODO Implement requestRegister
    }
}
