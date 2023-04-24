package me.hlatky.wbpo.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

fun AppCompatActivity.setupToolbar(toolbar: Toolbar?) {
    setSupportActionBar(toolbar)

    supportActionBar?.let {
        it.setDisplayHomeAsUpEnabled(false)
        it.setDisplayShowTitleEnabled(false)
    }
}
