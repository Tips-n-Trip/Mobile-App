package com.bangkitcapstone.tipsntrip.data.lib.city

import android.os.Parcelable
import com.bangkitcapstone.tipsntrip.data.lib.attraction.Attraction
import com.bangkitcapstone.tipsntrip.data.lib.souvenir.Souvenir
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Destination(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("image_path")
    val imagePath: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("about")
    val about: String,

    @field:SerializedName("attractions")
    val attractions: ArrayList<Attraction>? = null,

    @field:SerializedName("souvenirs")
    val souvenirs: ArrayList<Souvenir>? = null
) : Parcelable

@Parcelize
data class DestinationDetail(

    @field:SerializedName("image_path")
    val imagePath: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("about")
    val about: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("attractions")
    val attractions: ArrayList<Attraction>,

    @field:SerializedName("souvenirs")
    val souvenirs: ArrayList<Souvenir>,
) : Parcelable