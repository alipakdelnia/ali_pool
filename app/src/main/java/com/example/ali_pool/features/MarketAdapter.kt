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

                binding.txtCoinName.text = dataCoin.coinInfo.name
                binding.txtCoinPrice.text = dataCoin.dISPLAY.uSD.pRICE


                val change = dataCoin.rAW.uSD.cHANGEPCT24HOUR
                    if (change > 0) {
                        binding.txtCoinChange.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.colorGain
                            )
                        )
                        binding.txtCoinChange.text =
                            dataCoin.dISPLAY.uSD.cHANGEPCT24HOUR
                    } else if (change < 0) {
                        binding.txtCoinChange.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.colorLoss
                            )
                        )
                        binding.txtCoinChange.text =
                            dataCoin.dISPLAY.uSD.cHANGEPCT24HOUR
                    } else {
                        binding.txtCoinChange.text =  dataCoin.dISPLAY.uSD.cHANGEPCT24HOUR
                        binding.txtCoinChange.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.tertiaryTextColor
                            )
                        )
                    }

                    binding.txtCoinCap.text = dataCoin.dISPLAY.uSD.mKTCAP

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