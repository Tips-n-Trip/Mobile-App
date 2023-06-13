package com.bangkitcapstone.tipsntrip.data.lib.itenerary

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Agenda(

    @field:SerializedName("iteneraryId")
    val iteneraryId: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("attractions")
    val attractions: ArrayList<AttractionsItenerary>,

    @field:SerializedName("day")
    val day: Int
):Parcelable
