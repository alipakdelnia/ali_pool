package com.example.ali_pool.apiManager


import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://min-api.cryptocompare.com/data/"
const val BASE_URL_IMAGE = "https://www.cryptocompare.com"
const val API_KEY = "2ebf6107b0a145813a8210c54e750a207ceb9da4cd1cae9dc206bcbf97fb4735"
const val APP_NAME = "test app"

class ApiManager {
    private val apiSecvice: ApiService

    init {

        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiSecvice = retrofit.create(ApiService::class.java)

    }


    interface ApiCallback<T> {

        fun onSuccess(data: T)

        fun onError(errorMessager: String)

    }
}