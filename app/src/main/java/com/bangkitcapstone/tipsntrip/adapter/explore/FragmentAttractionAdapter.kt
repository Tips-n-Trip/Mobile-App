package com.bangkitcapstone.tipsntrip.adapter.explore

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.tipsntrip.data.lib.attraction.Attraction
import com.bangkitcapstone.tipsntrip.databinding.CardviewAttractionBinding
import com.bangkitcapstone.tipsntrip.ui.detail.attraction.AttractionDetailActivity
import com.bangkitcapstone.tipsntrip.utils.Helper
import com.bumptech.glide.Glide

class FragmentAttractionAdapter:PagingDataAdapter<Attraction,FragmentAttractionAdapter.ViewHolder>(DIFF_CALLBACK){
    class ViewHolder( var binding : CardviewAttractionBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : Attraction){
            binding.apply {
                tvAttractionName.text = data.name
                tvAttractionLocation.text = Helper.parseCityAddress(itemView.context, data.latitude, data.longitude)
                Glide.with(itemView.context)
                    .load(data.imagePath)
                    .into(ivBanner)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context,AttractionDetailActivity::class.java)
                intent.putExtra("DATA ATTRACTION",data)
                itemView.context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CardviewAttractionBinding.inflate(LayoutInflater.from(parent.context),parent,false))



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.bind(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Attraction>() {
            override fun areItemsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Attraction, newItem: Attraction): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }



}