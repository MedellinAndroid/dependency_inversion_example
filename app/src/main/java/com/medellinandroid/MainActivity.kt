package com.medellinandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query


data class User(
    val email: String,
    val name: String,
    val id: String
)

interface LoginService {
    @POST("login")
    fun login(@Query("email") email: String?, @Query("password") password: String?)
            : Call<User>?
}

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

            val retrofit = Retrofit.Builder()
                .client(OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = (HttpLoggingInterceptor.Level.BODY)
                        }
                    ).build()
                )
                .baseUrl("https://602db10196eaad00176dcb6d.mockapi.io")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val service: LoginService = retrofit.create(LoginService::class.java)

            val call = service.login(email, password)
            call?.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    progressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val user = response.body()

                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("Hola!")
                            .setMessage("Bienvenido ${user?.name ?: ""}!")
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

                override fun onFailure(call: Call<User>, t: Throwable) {
                    progressBar.visibility = View.GONE

                    Log.e("Login", "Error", t)

                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Error!")
                        .setMessage("Algo paso 2!")
                        .setPositiveButton(android.R.string.yes, null)
                        .setNegativeButton(android.R.string.no, null)
                        .show()
                }
            })
        }
    }
}