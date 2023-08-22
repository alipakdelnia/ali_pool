package com.example.ali_pool.apiManager


import com.example.ali_pool.apiManager.model.User
import retrofit2.Call
import retrofit2.http.GET

interface ApiSecvice {

    @GET("users")
    fun getUsers(): Call<List<User>>

}