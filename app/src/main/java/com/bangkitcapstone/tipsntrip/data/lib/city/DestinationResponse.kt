package com.bangkitcapstone.tipsntrip.data.lib.city

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DestinationResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("destinations")
	val destinations: ArrayList<Destination>,

	@field:SerializedName("message")
	val message: String
): Parcelable

