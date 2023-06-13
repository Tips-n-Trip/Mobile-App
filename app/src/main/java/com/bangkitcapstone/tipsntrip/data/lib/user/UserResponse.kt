package com.bangkitcapstone.tipsntrip.data.lib.user

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val userdata: User
)

data class User(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("total_iteneraries")
	val totalIteneraries: Int,

	@field:SerializedName("email")
	val email: String
)
