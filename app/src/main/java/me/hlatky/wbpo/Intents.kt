package me.hlatky.wbpo

import android.content.Context
import android.content.Intent
import android.net.Uri

object Intents {
    fun showTerms(context: Context) =
        Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.user_terms_url)))
}
