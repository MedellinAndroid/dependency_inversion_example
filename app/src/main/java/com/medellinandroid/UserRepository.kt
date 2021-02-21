package com.medellinandroid

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

class Api {
    fun buildUserRepository(): UserRepository {
        val retrofit = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
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
        return UserRepository(service)
    }
}

class UserRepository(private val service: LoginService) {
    fun login(email: String, password: String, responseCallback: (User?) -> Unit) {
        val call = service.login(email, password)

        call?.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    responseCallback(user)
                } else {
                    responseCallback(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                responseCallback(null)
            }
        })
    }
}

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