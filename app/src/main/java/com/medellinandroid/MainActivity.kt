package com.medellinandroid

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val email = findViewById<EditText>(R.id.login_email).text.toString()
            val password = findViewById<EditText>(R.id.login_password).text.toString()

            val url = URL("https://602db10196eaad00176dcb6d.mockapi.io/login")

            val urlConnection = url.openConnection() as HttpURLConnection
            if(urlConnection.responseCode == HttpsURLConnection.HTTP_OK){
                urlConnection.res
            }
            else {
                response = "FAILED"; // See documentation for more info on response handling
            }

        }
    }
}