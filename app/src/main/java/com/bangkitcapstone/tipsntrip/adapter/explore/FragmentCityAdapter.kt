package com.bangkitcapstone.tipsntrip.adapter.explore

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.tipsntrip.data.lib.city.Destination
import com.bangkitcapstone.tipsntrip.databinding.CardviewCityBinding
import com.bangkitcapstone.tipsntrip.ui.detail.city.CityDetailActivity
import com.bumptech.glide.Glide

class FragmentCityAdapter : PagingDataAdapter<Destination,FragmentCityAdapter.ViewHolder>(DIFF_CALLBACK){


    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Destination>(){
            override fun areItemsTheSame(oldItem: Destination, newItem: Destination): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Destination, newItem: Destination): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
    class ViewHolder(var binding : CardviewCityBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Destination){
            binding.apply {
                labelCityFragment.text = data.name
                Glide.with(itemView.context)
                    .load(data.imagePath)
                    .into(ivBannerCityFragment)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context,CityDetailActivity::class.java)
                intent.putExtra("ID",data.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardviewCityBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.bind(data)
        }
    }



}


