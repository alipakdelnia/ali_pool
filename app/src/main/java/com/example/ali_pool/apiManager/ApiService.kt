package com.example.ali_pool.apiManager

import com.example.ali_pool.apiManager.model.CoinsInfo
import com.example.ali_pool.apiManager.model.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {

    @Headers(API_KEY)
    @GET ("v2/news/")
    fun gerTopNews (
        @Query ("sortOrder") sortOrder : String = "popular"
    ): Call<NewsData>

    @Headers (API_KEY)
    @GET ("top/totalvolfull")
    fun getCoinsInfo (
        @Query("tsym") to_symbol: String = "USD",
        @Query("limit") limit_data : Int = 10
    ) : Call<CoinsInfo>

}