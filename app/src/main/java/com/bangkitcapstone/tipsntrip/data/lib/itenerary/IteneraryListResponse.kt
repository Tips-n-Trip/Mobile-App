package com.bangkitcapstone.tipsntrip.data.lib.itenerary

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class IteneraryListResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("iteneraries")
	val iteneraries: ArrayList<Itenerary>,

	@field:SerializedName("message")
	val message: String,
) : Parcelable

