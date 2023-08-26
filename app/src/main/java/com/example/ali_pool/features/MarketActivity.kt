package com.example.ali_pool.features

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ali_pool.apiManager.ApiManager
import com.example.ali_pool.databinding.ActivityMarketBinding


class MarketActivity : AppCompatActivity() {
    lateinit var binding: ActivityMarketBinding
    val apiManager = ApiManager()
    lateinit var dataNews : ArrayList<Pair<String,String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutToolbar.toolbar.title = "Market"

        initUi ()


    }

    private fun initUi() {
        getNewsFromApi()
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
}