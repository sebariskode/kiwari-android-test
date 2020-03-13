package com.bobbycacra.simplechat

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bobbycacra.simplechat.model.Message
import com.bobbycacra.simplechat.model.User
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_chatroom.*
import java.util.*


class ChatRoomActivity : AppCompatActivity() {
    lateinit var sharedpreferences: SharedPreferences

    companion object {
        const val TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatroom)

        sharedpreferences =
            getSharedPreferences("simple_chat", Context.MODE_PRIVATE)
        val user = Gson().fromJson<User>(sharedpreferences.getString("user", ""), User::class.java)
        val db = FirebaseFirestore.getInstance()
        val adapter = MessageAdapter()

        toolbar.title = " "
        setSupportActionBar(toolbar)

        user_name.text = user.name
        Glide
            .with(this)
            .load(user.avatar)
            .centerCrop()
            .into(avatar)

        val docRef = db.collection("messages")
            .orderBy("date", Query.Direction.ASCENDING)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.documents.isNotEmpty()) {
                adapter.messages.clear()
                for (document in snapshot.documents) {

                    val date = (document.data!!["date"] as Timestamp).toDate()
                    adapter.messages.add(
                        Message(
                            document.data!!["from"].toString(),
                            date.toString(),
                            document.data!!["text_content"].toString()
                        )
                    )
                    adapter.notifyDataSetChanged()
                }
                Log.d(TAG, "Current data: ${snapshot.documents}")
            } else {
                Log.d(TAG, "Current data: null")
            }
        }

        rv_messages.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_messages.adapter = adapter

        btn_send.setOnClickListener {
            val textMessage = message.text.toString()

            if (textMessage.isBlank()) {
                return@setOnClickListener
            }

            val messageHashMap = hashMapOf(
                "from" to user.name,
                "text_content" to textMessage,
                "date" to Timestamp(Date())
            )

            db.collection("messages")
                .add(messageHashMap)
                .addOnSuccessListener {
                    Log.d(TAG, "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener {
                        e -> Log.w(TAG, "Error writing document", e)
                }

            message.setText("")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_logout -> {
                val editor: SharedPreferences.Editor = sharedpreferences.edit()
                editor.clear()
                editor.apply()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
