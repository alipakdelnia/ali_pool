package com.example.ali_pool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ali_pool.apiManager.ApiManager
import com.example.ali_pool.apiManager.ApiSecvice
import com.example.ali_pool.databinding.ActivityMainBinding
import com.example.ali_pool.apiManager.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MainActivity : AppCompatActivity() {
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