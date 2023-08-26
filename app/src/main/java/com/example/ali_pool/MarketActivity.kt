package com.example.ali_pool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ali_pool.databinding.ActivityMarketBinding


class MarketActivity : AppCompatActivity() {
    lateinit var binding: ActivityMarketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutToolbar.toolbar.title = "Market"


    }
}