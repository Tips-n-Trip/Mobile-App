package com.bangkitcapstone.tipsntrip.data.lib.city

import com.google.gson.annotations.SerializedName

data class DetailDestinationResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("total_objek")
	val totalObjek: Int,

	@field:SerializedName("destination")
	val destination: Destination,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("total_sentra")
	val totalSentra: Int
)


