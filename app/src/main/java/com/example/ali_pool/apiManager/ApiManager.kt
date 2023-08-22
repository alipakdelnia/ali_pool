package com.example.ali_pool.apiManager

import com.example.ali_pool.apiManager.model.User
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val base_url = "https://jsonplaceholder.typicode.com/"

class ApiManager {
    private val apiSecvice : ApiSecvice

    init {

        val retrofit = Retrofit
            .Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

         apiSecvice = retrofit.create(ApiSecvice::class.java)

    }

    fun getUsers(callback : ApiCallback<List<User>>){

        apiSecvice.getUsers().execute(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val body = response.body()!!
                callback.onSuccess(body)
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                val message = t.message!!
                callback.onError(message)
            }
        })
    }

    interface ApiCallback<T> {

        fun onSuccess (data : T)

        fun onError(errorMessager : String)

    }
}