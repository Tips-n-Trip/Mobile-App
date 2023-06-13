package com.bangkitcapstone.tipsntrip.ui.explore.destination

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.data.lib.city.DestinationResponse
import com.bangkitcapstone.tipsntrip.data.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityDestinationViewModel:ViewModel() {
    private var _destination = MutableLiveData<DestinationResponse>()
    val destination : LiveData<DestinationResponse> = _destination
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getAllDataDestination(context : Context){
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getAllDestination()
        client.enqueue(object : Callback<DestinationResponse>{
            override fun onResponse(
                call: Call<DestinationResponse>,
                response: Response<DestinationResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _destination.value = response.body()
                }
                else{
                    _error.postValue("ERROR ${response.code()} : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DestinationResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Failed to Fetch Data", "onFailure Call: ${t.message}")
                _error.postValue("${context.getString(R.string.destination_error)} : ${t.message}")

            }

        })
    }
}
