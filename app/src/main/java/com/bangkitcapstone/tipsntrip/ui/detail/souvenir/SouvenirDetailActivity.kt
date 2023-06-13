package com.bangkitcapstone.tipsntrip.ui.detail.souvenir

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bangkitcapstone.tipsntrip.data.lib.souvenir.Souvenir
import com.bangkitcapstone.tipsntrip.databinding.ActivitySouvenirDetailBinding
import com.bangkitcapstone.tipsntrip.utils.Helper
import com.bumptech.glide.Glide

@Suppress("DEPRECATION")
class SouvenirDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySouvenirDetailBinding

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySouvenirDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dataSouvenir = intent.getParcelableExtra<Souvenir>("DATA SOUVENIR") as Souvenir
        val imagePath = intent.getStringExtra("IMAGE")
        Glide.with(this)
            .load(imagePath)
            .into(binding.ivBannerDetailSouvenir)
        binding.apply {
            tvSouvenirName.text = dataSouvenir.name
            tvSouvenirAddress.text = Helper.parseAddress(this@SouvenirDetailActivity, dataSouvenir.latitude, dataSouvenir.longitude)
            tvSouvenirDescription.text = dataSouvenir.about
            tvOpenHourValue.text = dataSouvenir.openHour
        }
        binding.btnDetailSouvenirGetMaps.setOnClickListener {
            val location = "${dataSouvenir.latitude},${dataSouvenir.longitude}"
            val gmmIntentUri = Uri.parse("geo:0,0?q=$location")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(packageManager).let {
                startActivity(mapIntent)
            }
        }
        binding.btnBackDetailSouvenir.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}