package com.medellinandroid

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val progressBar = findViewById<View>(R.id.login_progress).apply {
                visibility = View.VISIBLE
            }

            val email = findViewById<EditText>(R.id.login_email).text.toString()
            val password = findViewById<EditText>(R.id.login_password).text.toString()

            val userRepository = Api().buildUserRepository()

            userRepository.login(email, password) {
                progressBar.visibility = View.GONE

                if (it != null) {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Hola!")
                        .setMessage("Bienvenido ${it.name}!")
                        .setPositiveButton(android.R.string.yes, null)
                        .setNegativeButton(android.R.string.no, null)
                        .show()
                } else {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Error!")
                        .setMessage("Algo paso 1!")
                        .setPositiveButton(android.R.string.yes, null)
                        .setNegativeButton(android.R.string.no, null)
                        .show()
                }
            }
        }
    }
}