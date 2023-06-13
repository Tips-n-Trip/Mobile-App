package com.bangkitcapstone.tipsntrip.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.data.lib.attraction.AttractionResponse
import com.bangkitcapstone.tipsntrip.data.lib.city.DestinationResponse
import com.bangkitcapstone.tipsntrip.data.lib.souvenir.SouvenirResponse
import com.bangkitcapstone.tipsntrip.data.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel : ViewModel() {
    private var _destination = MutableLiveData<DestinationResponse>()
    val destination : LiveData<DestinationResponse> = _destination
    private var _attraction = MutableLiveData<AttractionResponse>()
    val attraction: LiveData<AttractionResponse> = _attraction
    private var _souvenir = MutableLiveData<SouvenirResponse>()
    val souvenir: LiveData<SouvenirResponse> = _souvenir
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getAllDestination(context: Context) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getAllDestination()
        client.enqueue(object : Callback<DestinationResponse> {
            override fun onResponse(
                call: Call<DestinationResponse>,
                response: Response<DestinationResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _destination.postValue(response.body())
                } else {
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

    fun getAllAttraction(context: Context) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getAllAttraction()
        client.enqueue(object : Callback<AttractionResponse> {
            override fun onResponse(
                call: Call<AttractionResponse>,
                response: Response<AttractionResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _attraction.postValue(response.body())
                } else {
                    _error.postValue("ERROR ${response.code()} : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AttractionResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Failed to Fetch Data", "onFailure Call: ${t.message}")
                _error.postValue("${context.getString(R.string.attraction_error)} : ${t.message}")
            }

        })
    }

    fun getAllSouvenir(context: Context) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getAllSouvenir()
        client.enqueue(object : Callback<SouvenirResponse> {
            override fun onResponse(
                call: Call<SouvenirResponse>,
                response: Response<SouvenirResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _souvenir.postValue(response.body())
                } else {
                    _error.postValue("ERROR ${response.code()} : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SouvenirResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Failed to Fetch Data", "onFailure Call: ${t.message}")
                _error.postValue("${context.getString(R.string.souvenir_error)} : ${t.message}")
            }

        })
    }

}