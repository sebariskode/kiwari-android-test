package com.bobbycacra.simplechat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bobbycacra.simplechat.model.User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var sharedpreferences: SharedPreferences =
            getSharedPreferences("simple_chat", Context.MODE_PRIVATE)

        // hardcoded user
        val users: List<User> = listOf(
            User(
                "Jarjit Singh",
                "https://api.adorable.io/avatars/160/jarjit@mail.com.png",
                "jarjit@mail.com",
                "123456"
            )
            , User(
                "Ismail bin Mail",
                "https://api.adorable.io/avatars/160/ismail@mail.com.png",
                "ismail@mail.com",
                "123456"
            )
        )

        btn_login.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please insert email/password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            for (user in users) {
                if (email == user.email && password == user.password) {
                    val editor: SharedPreferences.Editor = sharedpreferences.edit()
                    editor.putString("user", Gson().toJson(user))
                    editor.apply()

                    startActivity(
                        Intent(this, ChatRoomActivity::class.java)
                    )
                    finish()

                    return@setOnClickListener
                }
            }

            Toast.makeText(this, "Email/password is wrong", Toast.LENGTH_SHORT).show()
        }
    }
}
