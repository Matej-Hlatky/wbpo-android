package me.hlatky.wbpo.util

import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard(): Boolean? = context.getSystemService(InputMethodManager::class.java)
    ?.hideSoftInputFromWindow(windowToken, 0)
