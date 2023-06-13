package com.bangkitcapstone.tipsntrip.adapter.explore

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.tipsntrip.data.lib.souvenir.Souvenir
import com.bangkitcapstone.tipsntrip.databinding.CardviewSouvenirBinding
import com.bangkitcapstone.tipsntrip.ui.detail.souvenir.SouvenirDetailActivity
import com.bangkitcapstone.tipsntrip.utils.Helper
import com.bumptech.glide.Glide

class FragmentSouvenirAdapter :
    PagingDataAdapter<Souvenir, FragmentSouvenirAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(var binding: CardviewSouvenirBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Souvenir) {
            binding.apply {
                tvStoreName.text = data.name
                tvStoreLocation.text = Helper.parseCityAddress(itemView.context, data.latitude, data.longitude)
                Glide.with(itemView.context)
                    .load(data.imagePath)
                    .into(ivBanner)
            }
            itemView.setOnClickListener {
                val intentDetailSouvenirFragment =
                    Intent(itemView.context, SouvenirDetailActivity::class.java)
                intentDetailSouvenirFragment.putExtra("DATA SOUVENIR", data)
                itemView.context.startActivity(intentDetailSouvenirFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CardviewSouvenirBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Souvenir>() {
            override fun areItemsTheSame(oldItem: Souvenir, newItem: Souvenir): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Souvenir, newItem: Souvenir): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}
