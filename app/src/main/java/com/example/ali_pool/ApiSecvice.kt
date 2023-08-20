package com.example.ali_pool


import com.example.ali_pool.model.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiSecvice {

    @GET("users")
    fun getUsers(): Call<List<User>>

}