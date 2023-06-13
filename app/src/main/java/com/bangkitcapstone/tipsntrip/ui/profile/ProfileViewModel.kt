package com.bangkitcapstone.tipsntrip.ui.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkitcapstone.tipsntrip.data.lib.user.UserResponse
import com.bangkitcapstone.tipsntrip.data.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    private var _user = MutableLiveData<UserResponse>()
    val user: LiveData<UserResponse> = _user
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getUserProfile(context: Context) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).getUserProfile()
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.postValue(response.body())
                } else {
                    _error.postValue("ERROR ${response.code()} : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                t.message?.let { Log.d("Failure", it) }
            }

        })
    }


}