package com.bangkitcapstone.tipsntrip.data.lib.souvenir

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SouvenirResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("souvenirs")
	val souvenirs: ArrayList<Souvenir>
): Parcelable

@Parcelize
data class Souvenir(

	@field:SerializedName("image_path")
	val imagePath: String,

	@field:SerializedName("latitude")
	val latitude: Double,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("about")
	val about: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("open_hour")
	val openHour: String,

	@field:SerializedName("destinationId")
	val destinationId: String,

	@field:SerializedName("longitude")
	val longitude: Double
):Parcelable
