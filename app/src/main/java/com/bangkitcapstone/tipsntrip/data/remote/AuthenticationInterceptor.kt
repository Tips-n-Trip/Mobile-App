package com.bangkitcapstone.tipsntrip.data.remote

import android.content.Context
import com.bangkitcapstone.tipsntrip.utils.UserPreference
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(context: Context) : Interceptor {
    private val userPreference = UserPreference(context)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        userPreference.getToken()?.let { token->
            request
                .addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(request.build())
    }
}