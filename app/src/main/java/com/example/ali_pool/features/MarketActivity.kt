package com.example.ali_pool.features

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ali_pool.apiManager.ApiManager
import com.example.ali_pool.apiManager.model.CoinsInfo
import com.example.ali_pool.databinding.ActivityMarketBinding


class MarketActivity : AppCompatActivity() , MarketAdapter.RecyclerCallback {
    lateinit var binding: ActivityMarketBinding
    val apiManager = ApiManager()
    lateinit var adapter: MarketAdapter
    lateinit var dataNews : ArrayList<Pair<String,String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.layoutToolbar.toolbar.title = "Market"

        binding.layoutWatchlist.btShowMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW , Uri.parse("https://www.livecoinwatch.com/"))
            startActivity(intent)
        }

        binding.swipeRefreshMain.setOnRefreshListener {

            initUi ()

            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshMain.isRefreshing = false
            },1000)
        }
    }

    override fun onPause() {
        super.onPause()

        initUi()
    }

    override fun onResume() {
        super.onResume()

        initUi()

    }

    override fun onRestart() {
        super.onRestart()
    }

    private fun initUi() {
        getNewsFromApi()
        getTopCoinsFromApi()
    }

    private fun getTopCoinsFromApi() {

        apiManager.getCoinsList(object : ApiManager.ApiCallback<List<CoinsInfo.Data>>{
            override fun onSuccess(data: List<CoinsInfo.Data>) {
                showDataInRecycler(data )
            }
            override fun onError(errorMessager: String) {
                Toast.makeText(this@MarketActivity, "error => " + errorMessager, Toast.LENGTH_SHORT)
                    .show()
                Log.v("testLog", errorMessager)
            }
        })
    }

    private fun showDataInRecycler(data : List<CoinsInfo.Data>) {

        adapter = MarketAdapter(ArrayList(data),this)
        binding.layoutWatchlist.recyclerViewMain.adapter = adapter
        binding.layoutWatchlist.recyclerViewMain.layoutManager = LinearLayoutManager(this)



    }

    private fun getNewsFromApi(){

        apiManager.getNews(object : ApiManager.ApiCallback<ArrayList<Pair<String,String>>>{
            override fun onSuccess(data: ArrayList<Pair<String, String>>) {
                dataNews = data
                refreshNews()
            }

            override fun onError(errorMessager: String) {
                Toast.makeText(this@MarketActivity, "error => $errorMessager", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun refreshNews() {
        val randomAccess = (0..49).random()
        binding.layoutNews.txtNews.text = dataNews[randomAccess].first
        binding.layoutNews.imgNews.setOnClickListener {
            val intent  = Intent(Intent.ACTION_VIEW, Uri.parse(dataNews[randomAccess].second))
            startActivity(intent)
        }
        binding.layoutNews.txtNews.setOnClickListener{
            refreshNews()
        }

    }

    override fun onCoinItemClicked(dataCoin: CoinsInfo.Data) {
        val intent = Intent(this,coinActivity::class.java)
        intent.putExtra("dataToSend",dataCoin)
        startActivity(intent)
    }
}