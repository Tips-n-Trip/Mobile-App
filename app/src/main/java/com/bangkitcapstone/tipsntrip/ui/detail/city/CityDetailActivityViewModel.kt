package com.bangkitcapstone.tipsntrip.ui.detail.city

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.data.lib.city.DetailDestinationResponse
import com.bangkitcapstone.tipsntrip.data.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityDetailActivityViewModel:ViewModel() {
    private var _destinationDetail = MutableLiveData<DetailDestinationResponse>()
    val destinationDetail : LiveData<DetailDestinationResponse> = _destinationDetail
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    fun getDetailDestination(context: Context, id: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getDetailDestination(id)
        client.enqueue(object : Callback<DetailDestinationResponse> {
            override fun onResponse(call: Call<DetailDestinationResponse>, response: Response<DetailDestinationResponse>) {
                _isLoading.value = true
                if (response.isSuccessful){
                    _destinationDetail.postValue(response.body())
                }else{
                    _error.postValue("ERROR ${response.code()} : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailDestinationResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e("Failed to Fetch Data", "onFailure Call: ${t.message}")
                _error.postValue("${context.getString(R.string.destination_error)} : ${t.message}")
            }

        })
    }
}