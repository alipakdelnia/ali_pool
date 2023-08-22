package com.example.ali_pool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ali_pool.apiManager.ApiManager
import com.example.ali_pool.databinding.ActivityMainBinding
import com.example.ali_pool.apiManager.model.User


class MarketActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val apiManager = ApiManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btGetData.setOnClickListener {

            apiManager.getUsers(object : ApiManager.ApiCallback<List<User>>{
                override fun onSuccess(data: List<User>) {
                    Log.v("ApiTEst",data.toString())
                }

                override fun onError(errorMessager: String) {
                    Log.v("ApiTEst",errorMessager)
                }


            })


        }
    }
}