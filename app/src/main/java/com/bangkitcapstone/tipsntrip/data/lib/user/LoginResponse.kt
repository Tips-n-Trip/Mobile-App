package com.bangkitcapstone.tipsntrip.data.lib.user

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("credentials")
	val credentials: Credentials,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Credentials(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("token")
	val token: String
)
