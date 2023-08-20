package com.example.ali_pool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ali_pool.databinding.ActivityMainBinding
import com.example.ali_pool.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val base_url = "https://jsonplaceholder.typicode.com/"

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btGetData.setOnClickListener {

            val retrofit = Retrofit
                .Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiSecvice = retrofit.create(ApiSecvice::class.java)

            val call = apiSecvice.getUsers()

            call.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    val data = response.body()
                    Log.v("testRetrofit",data.toString())
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }


            } )

        }
    }
}