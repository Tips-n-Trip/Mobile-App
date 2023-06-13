package com.bangkitcapstone.tipsntrip.adapter.home


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.tipsntrip.data.lib.attraction.Attraction
import com.bangkitcapstone.tipsntrip.databinding.CardviewAttractionSmallBinding
import com.bangkitcapstone.tipsntrip.ui.detail.attraction.AttractionDetailActivity
import com.bangkitcapstone.tipsntrip.utils.Helper
import com.bumptech.glide.Glide

class SmallAttractionAdapter(private val listData: ArrayList<Attraction>) :
    RecyclerView.Adapter<SmallAttractionAdapter.ViewHolder>() {
    class ViewHolder(var binding: CardviewAttractionSmallBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CardviewAttractionSmallBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun bind(data: Attraction) {
            holder.binding.apply {
                with(holder.itemView) {
                    Glide.with(context)
                        .load(data.imagePath)
                        .into(ivBannerSmall)
                    tvAttractionName.text = data.name
                    tvAttractionLocation.text =
                        Helper.parseCityAddress(context, data.latitude, data.longitude)
                    this.setOnClickListener {
                        val intent = Intent(context, AttractionDetailActivity::class.java)
                        intent.putExtra("DATA ATTRACTION", data)
                        context.startActivity(intent)
                    }
                }
            }


        }
        bind(listData[position])
    }

    override fun getItemCount() = listData.size
}

