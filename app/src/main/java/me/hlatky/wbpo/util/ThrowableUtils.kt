package me.hlatky.wbpo.util

import android.content.res.Resources
import me.hlatky.wbpo.R

fun Throwable.getLocalizedUserFacingMessage(res: Resources): String {
    // Would switch Exception types and return different string
    return res.getString(R.string.error_message, localizedMessage)
}
