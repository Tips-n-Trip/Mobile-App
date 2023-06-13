package com.bangkitcapstone.tipsntrip.data.lib.user

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)
