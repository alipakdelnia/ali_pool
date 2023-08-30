package com.example.ali_pool.apiManager.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class CoinAboutData : ArrayList<CoinAboutData.CoinAboutDataItem>(){
    @Parcelize
    data class CoinAboutDataItem(
        val currencyName: String,
        val info: Info
    ) : Parcelable {
        @Parcelize
        data class Info(
            val desc: String,
            val forum: String,
            val github: String,
            val reddit: String,
            val twt: String,
            val web: String
        ) : Parcelable
    }
}