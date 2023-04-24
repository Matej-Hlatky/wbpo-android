package me.hlatky.wbpo.util

import android.content.res.Resources
import me.hlatky.wbpo.R
import java.net.UnknownHostException

fun Throwable.getLocalizedUserFacingMessage(res: Resources): String = when (this) {
    is UnknownHostException -> res.getString(R.string.error_internet_message, localizedMessage)
    else -> res.getString(R.string.error_generic_message, localizedMessage)
}
