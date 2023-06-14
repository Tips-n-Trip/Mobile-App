package com.bangkitcapstone.tipsntrip.ui.detail.city

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.adapter.home.SmallAttractionAdapter
import com.bangkitcapstone.tipsntrip.adapter.home.SmallSouvenirAdapter
import com.bangkitcapstone.tipsntrip.data.lib.city.Destination
import com.bangkitcapstone.tipsntrip.databinding.ActivityCityDetailBinding
import com.bumptech.glide.Glide

class CityDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCityDetailBinding
    private val cityDetailActivityViewModel by viewModels<CityDetailActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val destinationData =
            intent.getParcelableExtra<Destination>("DESTINATION DATA") as Destination

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvAttractionDetailCity.layoutManager = layoutManager
        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvSouvenirDetailCity.layoutManager = layoutManager2
        binding.btnBackDetailCity.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.tvDestinationTitle.text =
            resources.getString(R.string.label_about_city, destinationData.name)
        binding.tvDestinationDetail.text = destinationData.about
        Glide.with(this)
            .load(destinationData.imagePath)
            .into(binding.ivDetailCity)
        setupAction(destinationData.id)

    }

    fun setupAction(destinationId: String) {
        cityDetailActivityViewModel.apply {
            getDetailDestination(this@CityDetailActivity, destinationId)
            destinationDetail.observe(this@CityDetailActivity) { response ->
                with(response) {
                    binding.apply {
                        tvTotalTouristAttraction.text = totalObjek.toString()
                        tvTotalSouvenirDetailCity.text = totalSentra.toString()
                        val attractionAdapter =
                            destination.attractions?.let {
                                SmallAttractionAdapter(it)
                            }
                        rvAttractionDetailCity.adapter = attractionAdapter
                        val souvenirAdapter = destination.souvenirs?.let {
                            SmallSouvenirAdapter(it)
                        }
                        rvSouvenirDetailCity.adapter = souvenirAdapter
                    }
                }

            }
        }
    }


}