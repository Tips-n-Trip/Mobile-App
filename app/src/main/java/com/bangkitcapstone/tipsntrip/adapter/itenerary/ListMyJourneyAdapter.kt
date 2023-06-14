package com.bangkitcapstone.tipsntrip.adapter.itenerary

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.tipsntrip.data.lib.itenerary.Itenerary
import com.bangkitcapstone.tipsntrip.databinding.CardviewMyJourneyBinding
import com.bangkitcapstone.tipsntrip.ui.itenerary.OutputIteneraryActivity
import com.bumptech.glide.Glide

class ListMyJourneyAdapter(private val listData : ArrayList<Itenerary>):RecyclerView.Adapter<ListMyJourneyAdapter.ViewHolder>() {
    class ViewHolder(var binding : CardviewMyJourneyBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = CardviewMyJourneyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun bind(data : Itenerary){
            holder.binding.apply {
                Glide.with(holder.itemView.context)
                    .load(data.destination.imagePath)
                    .into(ivBanner)
                tvItenenaryTitle.text = data.name
            }
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, OutputIteneraryActivity::class.java)
                intent.putExtra("DATA", data)
                holder.itemView.context.startActivity(intent)
                (holder.itemView.context as Activity).finish()
            }
        }
        bind(listData[position])
    }

    override fun getItemCount() = listData.size

}