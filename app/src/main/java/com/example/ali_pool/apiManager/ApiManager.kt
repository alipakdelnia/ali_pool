package com.example.ali_pool.apiManager


import com.example.ali_pool.apiManager.model.ChartData
import com.example.ali_pool.apiManager.model.CoinsInfo
import com.example.ali_pool.apiManager.model.NewsData
import ir.dunijet.dunipool.apiManager.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



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

    fun getCoinsList (apiCallback: ApiCallback<List<CoinsInfo.Data>>){
        apiService.getCoinsInfo().enqueue(object : Callback<CoinsInfo>{
            override fun onResponse(call: Call<CoinsInfo>, response: Response<CoinsInfo>) {
                val data = response.body()!!
                apiCallback.onSuccess(data.data)
            }

            override fun onFailure(call: Call<CoinsInfo>, t: Throwable) {
                apiCallback.onError(t.message!!)
            }

        })

    }

    fun getChartData(
        symbol: String,
        period: String,
        apiCallback: ApiCallback<Pair<List<ChartData.Data>, ChartData.Data?>>
    ) {

        var histoPeriod = ""
        var limit = 30
        var aggregate = 1

        when (period) {

            HOUR -> {
                histoPeriod = HISTO_MINUTE
                limit = 60
                aggregate = 12
            }

            HOURS24 -> {
                histoPeriod = HISTO_HOUR
                limit = 24
            }

            MONTH -> {
                histoPeriod = HISTO_DAY
                limit = 30
            }

            MONTH3 -> {
                histoPeriod = HISTO_DAY
                limit = 90
            }

            WEEK -> {
                histoPeriod = HISTO_HOUR
                aggregate = 6
            }

            YEAR -> {
                histoPeriod = HISTO_DAY
                aggregate = 13
            }

            ALL -> {
                histoPeriod = HISTO_DAY
                aggregate = 30
                limit = 2000
            }

        }

        apiService.getChartData(histoPeriod, symbol, limit, aggregate)
            .enqueue(object : Callback<ChartData> {
                override fun onResponse(call: Call<ChartData>, response: Response<ChartData>) {

                    val dataFull = response.body()!!
                    val data1 = dataFull.data
                    val data2 = dataFull.data.maxByOrNull { it.close.toFloat() }
                    val returningData = Pair(data1, data2)

                    apiCallback.onSuccess(returningData)

                }

                override fun onFailure(call: Call<ChartData>, t: Throwable) {
                    apiCallback.onError(t.message!!)
                }

            })

    }

    interface ApiCallback<T> {

        fun onSuccess(data: T)
        fun onError(errorMessager: String)

    }
}