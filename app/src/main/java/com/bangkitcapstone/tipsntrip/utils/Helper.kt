package com.bangkitcapstone.tipsntrip.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.system.Os.remove
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    private const val FILENAME_FORMAT = "dd-MMM-yyyy"
    private const val simpleDateFormat = "dd MMM yyyy HH.mm"
    private const val timestampFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    @SuppressLint("ConstantLocale")
    val simpleDate = SimpleDateFormat(simpleDateFormat, Locale.getDefault())

    val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

    fun parseAddress(context: Context, latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        val address: String = if (addresses?.isNotEmpty() == true) {
            val fetchedAddress: Address = addresses[0]
            // Extract the address details you need, e.g.
            fetchedAddress.getAddressLine(0) //complete address
//            "${fetchedAddress.subLocality}, ${fetchedAddress.locality}" //ambil nama desa & kecamatan
        } else {
            "Address not found"
        }
        return address
    }

    fun parseCityAddress(context: Context, latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        val address: String = if (addresses?.isNotEmpty() == true) {
            val fetchedAddress: Address = addresses[0]
            fetchedAddress.getAddressLine(0)
            val cityTemp = "${fetchedAddress.subAdminArea}"
            val city = cityTemp.split("\\s".toRegex()).toTypedArray()
            if (city[0] == "Kota"||city[0] == "Kabupaten") {
                if (city.size>2) return "${city[1]} ${city[2]}"
                else return city[1]
            }else{
                return cityTemp
            }

        } else {
            "Address not found"
        }
        return address
    }

}
