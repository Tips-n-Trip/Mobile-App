package com.bangkitcapstone.tipsntrip.data.lib.attraction

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Parcelize
data class AttractionResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("attractions")
	val attractions: ArrayList<Attraction>,

	@field:SerializedName("message")
	val message: String
) : Parcelable
@Parcelize
data class Attraction(

	@field:SerializedName("htm")
	val htm: Int,

	@field:SerializedName("image_path")
	val imagePath: String,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("about")
	val about: String,

	@field:SerializedName("recomendation_time")
	val recomendationTime: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("destinationId")
	val destinationId: String,

	@field:SerializedName("longitude")
	val longitude: Double
):Parcelable
