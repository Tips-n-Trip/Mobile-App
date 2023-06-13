package com.bangkitcapstone.tipsntrip.ui.detail.attraction

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.data.lib.attraction.Attraction
import com.bangkitcapstone.tipsntrip.databinding.ActivityAttractionDetailBinding
import com.bangkitcapstone.tipsntrip.utils.Helper
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class AttractionDetailActivity : AppCompatActivity() {
    private lateinit var attractionDetailBinding: ActivityAttractionDetailBinding


    @SuppressLint("QueryPermissionsNeeded", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attractionDetailBinding = ActivityAttractionDetailBinding.inflate(layoutInflater)
        setContentView(attractionDetailBinding.root)

        val dataAttraction = intent.getParcelableExtra<Attraction>("DATA ATTRACTION") as Attraction

        attractionDetailBinding.apply {
            Glide.with(applicationContext)
                .load(dataAttraction.imagePath)
                .into(ivBannerDetailAttraction)
            tvAttractionName.text = dataAttraction.name
            tvAttractionAbout.text = dataAttraction.about
            tvLocation.text = Helper.parseAddress(this@AttractionDetailActivity,
                dataAttraction.latitude,
                dataAttraction.longitude)
            if (dataAttraction.recomendationTime == "D") {
                tvTimeRecomendationValue.text = resources.getString(R.string.day_time)
                detailAttractionTimeRecommendation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_wb_sunny_24,
                    0,
                    0,
                    0)
                detailAttractionTimeRecommendation.compoundDrawablePadding = (24 * (resources.displayMetrics.density) + 0.5f).toInt()
            } else {
                tvTimeRecomendationValue.text = resources.getString(R.string.night_time)
                detailAttractionTimeRecommendation.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_nights_stay_24,
                    0,
                    0,
                    0)
                detailAttractionTimeRecommendation.compoundDrawablePadding = (24 * (resources.displayMetrics.density) + 0.5f).toInt()
            }
            tvAttractionPriceValue.text = resources.getString(R.string.price, dataAttraction.htm)
            tvBottomDestinationLabel.text = Helper.parseCityAddress(this@AttractionDetailActivity,
                dataAttraction.latitude,
                dataAttraction.longitude)
        }
        attractionDetailBinding.btnDetailAttractionGetMaps.setOnClickListener {
            val location = "${dataAttraction.latitude},${dataAttraction.longitude}"
            val gmmIntentUri = Uri.parse("geo:0,0?q=$location")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(packageManager).let {
                startActivity(mapIntent)
            }


        }
        attractionDetailBinding.btnBackDetailAttraction.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}