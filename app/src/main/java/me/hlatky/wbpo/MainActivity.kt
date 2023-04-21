package me.hlatky.wbpo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.hlatky.wbpo.ui.user.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // TODO Check if is logged-in or have token
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }
}
