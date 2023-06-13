package com.bangkitcapstone.tipsntrip.ui.signUp

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkitcapstone.tipsntrip.data.lib.user.LoginResponse
import com.bangkitcapstone.tipsntrip.data.lib.user.SignUpResponse
import com.bangkitcapstone.tipsntrip.data.remote.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    private var _user = MutableLiveData<SignUpResponse>()
    val user: LiveData<SignUpResponse> = _user
    private var _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun signUp(context: Context, name: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService(context).postRegister(email, name, password)
        client.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isLogin.postValue(true)
                    _user.postValue(response.body()) //you add here
                } else {
                    _error.postValue("ERROR ${response.code()} : ${response.message()}")
                    _isLogin.postValue(false)
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                _isLoading.value = false
                t.message?.let { Log.d("Failure", it) }
            }
        })
    }
}