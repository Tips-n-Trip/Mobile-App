package com.bangkitcapstone.tipsntrip.adapter.explore
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkitcapstone.tipsntrip.data.lib.city.Destination
import com.bangkitcapstone.tipsntrip.databinding.CardviewCityBinding
import com.bangkitcapstone.tipsntrip.ui.detail.city.CityDetailActivity
import com.bumptech.glide.Glide

class FragmentCityAdapter(private val listData: ArrayList<Destination>) :
    RecyclerView.Adapter<FragmentCityAdapter.ViewHolder>() {
    class ViewHolder(var binding: CardviewCityBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            CardviewCityBinding.inflate(LayoutInflater.from(parent.context),
                parent,
                false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun bind(data: Destination) {
            with(holder.itemView) {
                holder.binding.apply {
                    Glide.with(context)
                        .load(data.imagePath)
                        .into(ivBannerCityFragment)
                    labelCityFragment.text = data.name
                }
                this.setOnClickListener {
                    val intent = Intent(context, CityDetailActivity::class.java)
                    intent.putExtra("DESTINATION DATA", data)
                    context.startActivity(intent)
                }
            }
        }
        bind(listData[position])
    }


    override fun getItemCount() = listData.size

}
