package me.hlatky.wbpo.util

import android.content.res.Resources
import me.hlatky.wbpo.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.getLocalizedUserFacingMessage(res: Resources): String {
    val resId = when (this) {
        is UnknownHostException -> R.string.error_internet_message
        is SocketTimeoutException -> R.string.error_internet_message
        is ConnectException -> R.string.error_internet_message
        else -> R.string.error_generic_message
    }

    return res.getString(resId, localizedMessage)
}
