package com.bangkitcapstone.tipsntrip.data.lib.itenerary

import android.os.Parcelable
import com.bangkitcapstone.tipsntrip.data.lib.attraction.Attraction
import com.bangkitcapstone.tipsntrip.data.lib.city.Destination
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class IteneraryResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("itenerary")
	val itenerary: Itenerary
):Parcelable

@Parcelize
data class AttractionsItenerary(

	@field:SerializedName("attraction")
	val attraction: Attraction,

	@field:SerializedName("attractionId")
	val attractionId: String,

	@field:SerializedName("agendaId")
	val agendaId: String
): Parcelable

@Parcelize
data class Itenerary(
	@field:SerializedName("duration")
	val duration: Int,

	@field:SerializedName("agendas")
	val agendas: ArrayList<Agenda>,

	@field:SerializedName("isSaved")
	val isSaved: Boolean,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("destination")
	val destination: Destination,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("destinationId")
	val destinationId: String,

	@field:SerializedName("userId")
	val userId: String
):Parcelable

data class SaveIteneraryResponse(
	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,
)

data class DeleteIteneraryResponse(
	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String,
)