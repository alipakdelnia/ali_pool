package com.example.ali_pool.features


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ali_pool.apiManager.model.CoinsInfo
import com.example.ali_pool.databinding.ActivityCoinBinding

class coinActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoinBinding
    lateinit var dataThisCoin : CoinsInfo.Data
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataThisCoin = intent.getParcelableExtra<CoinsInfo.Data>("dataToSend")!!
        binding.layoutToolbar.toolbar.title = dataThisCoin.coinInfo.fullName

        initui()

    }

    private fun initui() {

        initChartUi()
        initStatisticsUi()
        initAboutUi()

    }

    private fun initAboutUi() {

    }

    @SuppressLint("SetTextI18n")
    private fun initStatisticsUi() {


        binding.layoutStatistics.tvOpenAmount.text = dataThisCoin.dISPLAY.uSD.oPEN24HOUR
        binding.layoutStatistics.tvTodaysHighAmount.text =dataThisCoin.dISPLAY.uSD.hIGH24HOUR
        binding.layoutStatistics.tvTodayLowAmount.text = dataThisCoin.dISPLAY.uSD.lOW24HOUR
        binding.layoutStatistics.tvChangeTodayAmount.text = dataThisCoin.dISPLAY.uSD.cHANGE24HOUR
        binding.layoutStatistics.tvalgorithm.text = dataThisCoin.coinInfo.algorithm
        binding.layoutStatistics.tvtotalvolume.text = dataThisCoin.dISPLAY.uSD.vOLUME24HOUR
        binding.layoutStatistics.tvAvgMarketCapAmount.text = dataThisCoin.dISPLAY.uSD.tOTALVOLUME24H
        binding.layoutStatistics.tvSupplyNumber.text = dataThisCoin.dISPLAY.uSD.sUPPLY


    }

    private fun initChartUi() {

    }
}