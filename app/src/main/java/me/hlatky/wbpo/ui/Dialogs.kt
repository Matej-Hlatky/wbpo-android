package me.hlatky.wbpo.ui

import android.content.Context
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import me.hlatky.wbpo.R

fun Context.showProgressDialog(@StringRes messageId: Int): AlertDialog {
    val inflater = LayoutInflater.from(this)
    val content = inflater.inflate(R.layout.dialog_progress_content, null, false)

    return MaterialAlertDialogBuilder(this)
        .setCancelable(false)
        .setMessage(messageId)
        .setView(content)
        .show()
}

fun Context.showErrorDialog(
    @StringRes titleId: Int = R.string.dialog_error_title,
    message: CharSequence,
    onPositiveButtonClick: () -> Unit = { }
): AlertDialog {
    return MaterialAlertDialogBuilder(this)
        .setTitle(titleId)
        .setMessage(message)
        .setCancelable(false)
        .setPositiveButton(R.string.dialog_ok) { _, _ -> onPositiveButtonClick() }
        .show()
}
