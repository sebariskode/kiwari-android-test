package com.bobbycacra.simplechat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        var sharedpreferences: SharedPreferences =
            getSharedPreferences("simple_chat", Context.MODE_PRIVATE)

        if (sharedpreferences.contains("user")) {
            startActivity(Intent(this, ChatRoomActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish()
    }
}
