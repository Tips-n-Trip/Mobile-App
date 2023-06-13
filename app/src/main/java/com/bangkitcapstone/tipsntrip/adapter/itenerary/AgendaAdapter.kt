package com.bangkitcapstone.tipsntrip.adapter.itenerary

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.data.lib.itenerary.AttractionsItenerary
import com.bangkitcapstone.tipsntrip.databinding.CardviewItenenaryOutputBinding
import com.bangkitcapstone.tipsntrip.ui.detail.attraction.AttractionDetailActivity
import com.bumptech.glide.Glide

class AgendaAdapter(private val listData: ArrayList<AttractionsItenerary>) :
    RecyclerView.Adapter<AgendaAdapter.ViewHolder>() {
    class ViewHolder(var binding: CardviewItenenaryOutputBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CardviewItenenaryOutputBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun bind(data: AttractionsItenerary) {
            with(holder.itemView.context) {
                holder.binding.apply {
                    if (data.attraction.recomendationTime == "D") {
                        tvTimeRecomendation.text = resources.getString(R.string.day_time)
                        icTimeRecomendation.setImageDrawable(resources.getDrawable(
                            R.drawable.ic_baseline_wb_sunny_24))
                    } else {
                        tvTimeRecomendation.text = resources.getString(R.string.night_time)
                        icTimeRecomendation.setImageDrawable(resources.getDrawable(
                            R.drawable.ic_baseline_nights_stay_24))
                    }
                    Glide.with(this@with)
                        .load(data.attraction.imagePath)
//                        .centerCrop()
                        .into(ivBannerSmall)
                    tvAttractionName.text = data.attraction.name
                }
            }
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, AttractionDetailActivity::class.java)
                intent.putExtra("DATA ATTRACTION", data.attraction)
                it.context.startActivity(intent)
            }
        }
        bind(listData[position])
    }

    override fun getItemCount() = listData.size
}
