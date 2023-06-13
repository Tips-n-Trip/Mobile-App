package com.bangkitcapstone.tipsntrip.ui.itenerary

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.data.lib.city.DestinationResponse
import com.bangkitcapstone.tipsntrip.data.lib.itenerary.DeleteIteneraryResponse
import com.bangkitcapstone.tipsntrip.data.lib.itenerary.IteneraryListResponse
import com.bangkitcapstone.tipsntrip.data.lib.itenerary.IteneraryResponse
import com.bangkitcapstone.tipsntrip.data.lib.itenerary.SaveIteneraryResponse
import com.bangkitcapstone.tipsntrip.data.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IteneraryViewModel: ViewModel() {
    private var _destinationChoices = MutableLiveData<DestinationResponse>()
    val destinationChoices: LiveData<DestinationResponse> = _destinationChoices
    private var _itenerary = MutableLiveData<IteneraryResponse>()
    val itenerary: LiveData<IteneraryResponse> = _itenerary
    private var _listItenerary = MutableLiveData<IteneraryListResponse>()
    val listItenerary: LiveData<IteneraryListResponse> = _listItenerary
    private var _saveItenerary = MutableLiveData<SaveIteneraryResponse>()
    val saveItenerary: LiveData<SaveIteneraryResponse> = _saveItenerary
    private var _unsaveItenerary = MutableLiveData<DeleteIteneraryResponse>()
    val unsaveItenerary: LiveData<DeleteIteneraryResponse> = _unsaveItenerary
    private var _deleteItenerary = MutableLiveData<DeleteIteneraryResponse>()
    val deleteItenerary: LiveData<DeleteIteneraryResponse> = _deleteItenerary
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getDestinationChoices(context:Context){
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getAllDestination()
        client.enqueue(object : Callback<DestinationResponse>{
            override fun onResponse(call: Call<DestinationResponse>, response: Response<DestinationResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _destinationChoices.postValue(response.body())
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

    fun postItenerary(context:Context, destination : String, duration: Int, budget: Int){
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).postItenerary(destination, duration, budget)
        client.enqueue(object : Callback<IteneraryResponse>{
            override fun onResponse(call: Call<IteneraryResponse>, response: Response<IteneraryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _itenerary.postValue(response.body())
                } else {
                    _error.postValue("ERROR ${response.code()} : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<IteneraryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Failed to Fetch Data", "onFailure Call: ${t.message}")
                _error.postValue("${context.getString(R.string.itenenary_error)} : ${t.message}")
            }

        })
    }

    fun getSavedItenerary(context: Context){
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getSavedItenerary()
        client.enqueue(object : Callback<IteneraryListResponse>{
            override fun onResponse(call: Call<IteneraryListResponse>, response: Response<IteneraryListResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listItenerary.postValue(response.body())
                } else {
                    _error.postValue("ERROR ${response.code()} : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<IteneraryListResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Failed to Fetch Data", "onFailure Call: ${t.message}")
                _error.postValue("${context.getString(R.string.itenenary_error)} : ${t.message}")
            }

        })
    }

    fun saveItenerarybyId(context:Context, id: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).saveItenerary(id)
        client.enqueue(object : Callback<SaveIteneraryResponse>{
            override fun onResponse(call: Call<SaveIteneraryResponse>, response: Response<SaveIteneraryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _saveItenerary.postValue(response.body())
                } else {
                    _error.postValue("ERROR ${response.code()} : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SaveIteneraryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Failed to Fetch Data", "onFailure Call: ${t.message}")
                _error.postValue("${context.getString(R.string.itenenary_save_error)} : ${t.message}")
            }

        })
    }
    fun unsaveItenerarybyId(context:Context, id: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).unsaveItenerary(id)
        client.enqueue(object : Callback<DeleteIteneraryResponse>{
            override fun onResponse(call: Call<DeleteIteneraryResponse>, response: Response<DeleteIteneraryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _unsaveItenerary.postValue(response.body())
                } else {
                    _error.postValue("ERROR ${response.code()} : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DeleteIteneraryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Failed to Fetch Data", "onFailure Call: ${t.message}")
                _error.postValue("${context.getString(R.string.itenenary_save_error)} : ${t.message}")
            }

        })
    }

    fun deleteItenerarybyId(context:Context, id: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).deleteItenerary(id)
        client.enqueue(object : Callback<DeleteIteneraryResponse>{
            override fun onResponse(call: Call<DeleteIteneraryResponse>, response: Response<DeleteIteneraryResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _deleteItenerary.postValue(response.body())
                } else {
                    _error.postValue("ERROR ${response.code()} : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DeleteIteneraryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Failed to Fetch Data", "onFailure Call: ${t.message}")
                _error.postValue("${context.getString(R.string.itenenary_delete_error)} : ${t.message}")
            }

        })
    }
}