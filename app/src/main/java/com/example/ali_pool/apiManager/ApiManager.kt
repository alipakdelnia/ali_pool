package com.example.ali_pool.apiManager


import com.example.ali_pool.apiManager.model.NewsData
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://min-api.cryptocompare.com/data/"
const val BASE_URL_IMAGE = "https://www.cryptocompare.com"
const val API_KEY = "authorization: Apikey 2ebf6107b0a145813a8210c54e750a207ceb9da4cd1cae9dc206bcbf97fb4735"
const val APP_NAME = "test app"

class ApiManager {
    private val apiService: ApiService

    init {

        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

    }

    fun getNews(apiCallback: ApiCallback<ArrayList<Pair<String,String>>>){
        apiService.getTopNews().enqueue(object : Callback<NewsData>{
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {

                val data = response.body()!!
                val dataToSend :ArrayList<Pair<String,String>> = arrayListOf()
                data.data.forEach{
                    dataToSend.add(Pair(it.title,it.url))
                }
                apiCallback.onSuccess(dataToSend)
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                apiCallback.onError(t.message!!)
            }

        })
    }

    interface ApiCallback<T> {

        fun onSuccess(data: T)

        fun onError(errorMessager: String)

    }
}