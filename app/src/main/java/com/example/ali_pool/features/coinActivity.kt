package com.example.ali_pool.features


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ali_pool.R
import com.example.ali_pool.apiManager.ApiManager
import com.example.ali_pool.apiManager.model.ChartData
import com.example.ali_pool.apiManager.model.CoinAboutItem
import com.example.ali_pool.apiManager.model.CoinsInfo
import com.example.ali_pool.databinding.ActivityCoinBinding
import ir.dunijet.dunipool.apiManager.*

class coinActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoinBinding
    lateinit var dataThisCoin : CoinsInfo.Data
    lateinit var dataThisCoinAbout : CoinAboutItem
    val apiManager = ApiManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fromIntent = intent.getBundleExtra("dataToSend")!!
        dataThisCoin = fromIntent.getParcelable<CoinsInfo.Data>("bundle1")!!
        dataThisCoinAbout = fromIntent.getParcelable<CoinAboutItem>("bundle2")!!

        binding.layoutToolbar.toolbar.title = dataThisCoin.coinInfo.fullName

        initui()

    }

    private fun initui() {

        initChartUi()
        initStatisticsUi()
        initAboutUi()

    }

    @SuppressLint("SetTextI18n")
    private fun initAboutUi() {

        binding.layoutAbout.txtWebsite.text = dataThisCoinAbout.coinWebsite
        binding.layoutAbout.txtGithub.text = dataThisCoinAbout.coinGithub
        binding.layoutAbout.txtReddit.text = dataThisCoinAbout.coinReddit
        binding.layoutAbout.txtTwitter.text = "@"+dataThisCoinAbout.coinTwitter
        binding.layoutAbout.txtAboutCoin.text = dataThisCoinAbout.coinDescription

        binding.layoutAbout.txtWebsite.setOnClickListener {
            openWebsiteDataCoin(dataThisCoinAbout.coinWebsite!!)
        }
        binding.layoutAbout.txtGithub.setOnClickListener {
            openWebsiteDataCoin(dataThisCoinAbout.coinGithub!!)
        }
        binding.layoutAbout.txtReddit.setOnClickListener {
            openWebsiteDataCoin(dataThisCoinAbout.coinReddit!!)
        }

    }

    private fun openWebsiteDataCoin(url : String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
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

    @SuppressLint("SetTextI18n")
    private fun initChartUi() {


        var period: String = HOUR
        requestAndShowChart(period)
        binding.layoutChart.radioGroupMain.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_12h -> {
                    period = HOUR
                }
                R.id.rb_1d -> {
                    period = HOURS24
                }
                R.id.rb_1w -> {
                    period = WEEK
                }
                R.id.rb_1m -> {
                    period = MONTH
                }
                R.id.rb_3m -> {
                    period = MONTH3
                }
                R.id.rb_1y -> {
                    period = YEAR
                }
                R.id.rb_all -> {
                    period = ALL
                }
            }
            requestAndShowChart(period)
        }

        binding.layoutChart.txtChartPrice.text = dataThisCoin.dISPLAY.uSD.pRICE
        binding.layoutChart.txtChartChange1.text = " " + dataThisCoin.dISPLAY.uSD.cHANGE24HOUR

        if (dataThisCoin.coinInfo.fullName == "BUSD") {
            binding.layoutChart.txtChartChange2.text = "0%"
        } else {
            binding.layoutChart.txtChartChange2.text = dataThisCoin.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0, 5) + "%"
        }

        val taghir = dataThisCoin.rAW.uSD.cHANGEPCT24HOUR
        if (taghir > 0) {

            binding.layoutChart.txtChartChange2.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorGain
                )
            )

            binding.layoutChart.txtChartUpdown.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorGain
                )
            )

            binding.layoutChart.txtChartUpdown.text = "▲"

            binding.layoutChart.chartSparkView.lineColor = ContextCompat.getColor(
                binding.root.context,
                R.color.colorGain
            )

        } else if (taghir < 0) {

            binding.layoutChart.txtChartChange2.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorLoss
                )
            )

            binding.layoutChart.txtChartUpdown.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.colorLoss
                )
            )

            binding.layoutChart.txtChartUpdown.text = "▼"

            binding.layoutChart.chartSparkView.lineColor = ContextCompat.getColor(
                binding.root.context,
                R.color.colorLoss
            )


        }

        binding.layoutChart.chartSparkView.setScrubListener {

            // show price kamel
            if ( it == null ) {
                binding.layoutChart.txtChartPrice.text = dataThisCoin.dISPLAY.uSD.pRICE
            } else {
                // show price this dot
                binding.layoutChart.txtChartPrice.text = "$ " + (it as ChartData.Data).close.toString()
            }

        }

    }

    fun requestAndShowChart(period: String) {

        apiManager.getChartData(
            dataThisCoin.coinInfo.name,
            period,
            object : ApiManager.ApiCallback<Pair<List<ChartData.Data>, ChartData.Data?>> {
                override fun onSuccess(data: Pair<List<ChartData.Data>, ChartData.Data?>) {
                    val chartAdapter = ChartAdapter(data.first, data.second?.open.toString())
                    binding.layoutChart.chartSparkView.adapter = chartAdapter
                }

                override fun onError(errorMessage: String) {
                    Toast.makeText(
                        this@coinActivity,
                        "error => " + errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }

}