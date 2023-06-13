package com.bangkitcapstone.tipsntrip.adapter.home


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.tipsntrip.data.lib.souvenir.Souvenir
import com.bangkitcapstone.tipsntrip.databinding.CardviewSouvenirSmallBinding
import com.bangkitcapstone.tipsntrip.ui.detail.souvenir.SouvenirDetailActivity
import com.bangkitcapstone.tipsntrip.utils.Helper
import com.bumptech.glide.Glide

class SmallSouvenirAdapter(private val listData: ArrayList<Souvenir>) :
    RecyclerView.Adapter<SmallSouvenirAdapter.ViewHolder>() {
    class ViewHolder(var binding: CardviewSouvenirSmallBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CardviewSouvenirSmallBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun bind(data: Souvenir) {
            holder.binding.apply {
                with(holder.itemView) {
                    Glide.with(context)
                        .load(data.imagePath)
                        .into(ivBannerSmall)
                    tvStoreName.text = data.name
                    tvStoreLocation.text = Helper.parseCityAddress(context, data.latitude, data.longitude)
                    holder.itemView.setOnClickListener {
                        val intent = Intent(it.context, SouvenirDetailActivity::class.java)
                        intent.putExtra("DATA SOUVENIR", data)
                        intent.putExtra("IMAGE", data.imagePath)
                        it.context.startActivity(intent)
                    }
                }
            }
        }
        bind(listData[position])
    }

    override fun getItemCount() = listData.size

}
