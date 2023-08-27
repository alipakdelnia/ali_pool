package com.example.ali_pool.features

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ali_pool.R
import com.example.ali_pool.apiManager.BASE_URL_IMAGE
import com.example.ali_pool.apiManager.model.CoinsInfo
import com.example.ali_pool.databinding.ItemRecyclerMarketBinding

class MarketAdapter(
    private var data : ArrayList<CoinsInfo.Data> ,
    private val recyclerCallback : RecyclerCallback
)  :
    RecyclerView.Adapter<MarketAdapter.MarketViewhHolder>() {
        lateinit var binding: ItemRecyclerMarketBinding

        inner class MarketViewhHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
            @SuppressLint("SetTextI18n")
            fun bindViews(dataCoin: CoinsInfo.Data){

                binding.txtCoinName.text = dataCoin.coinInfo.fullName
                binding.txtCoinPrice.text = "$"+dataCoin.rAW.uSD.pRICE.toString()

                val change = dataCoin.rAW.uSD.cHANGEPCT24HOUR
                if (change > 0 ){
                    binding.txtCoinChange.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorGain))
                    binding.txtCoinChange.text = dataCoin.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0,4)+"%"
            }else if (change<0){
                    binding.txtCoinChange.setTextColor(ContextCompat.getColor(binding.root.context, R.color.colorLoss))
                    binding.txtCoinChange.text = dataCoin.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0,5)+"%"
                }else {
                    binding.txtCoinChange.text = "0%"
                }

                val marketCap = dataCoin.rAW.uSD.mKTCAP / 1000000000
                val indexDot = marketCap.toString().indexOf('.')
                binding.txtCoinCap.text = "$"+marketCap.toString().substring(0,indexDot+3)+" B"

                Glide
                    .with(itemView)
                    .load(BASE_URL_IMAGE+dataCoin.coinInfo.imageUrl)
                    .into(binding.imgCoin)

                itemView.setOnClickListener {
                    recyclerCallback.onCoinItemClicked(dataCoin)
                }
            }
        }

    interface RecyclerCallback {
        fun onCoinItemClicked(dataCoin : CoinsInfo.Data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewhHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemRecyclerMarketBinding.inflate(inflater,parent,false)

        return MarketViewhHolder(binding.root)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MarketViewhHolder, position: Int) {
        holder.bindViews(data[position])
    }

}