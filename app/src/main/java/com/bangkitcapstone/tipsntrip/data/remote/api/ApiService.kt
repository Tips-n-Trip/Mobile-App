package com.bangkitcapstone.tipsntrip.data.remote.api

import com.bangkitcapstone.tipsntrip.data.lib.attraction.AttractionResponse
import com.bangkitcapstone.tipsntrip.data.lib.city.DestinationResponse
import com.bangkitcapstone.tipsntrip.data.lib.city.DetailDestinationResponse
import com.bangkitcapstone.tipsntrip.data.lib.itenerary.*
import com.bangkitcapstone.tipsntrip.data.lib.souvenir.SouvenirResponse
import com.bangkitcapstone.tipsntrip.data.lib.user.LoginResponse
import com.bangkitcapstone.tipsntrip.data.lib.user.SignUpResponse
import com.bangkitcapstone.tipsntrip.data.lib.user.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("auth/register")
    fun postRegister(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String,
    ): Call<SignUpResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<LoginResponse>

    @GET("user/profile")
    fun getUserProfile(): Call<UserResponse>

    @GET("destination")
    fun getAllDestination(
    ): Call<DestinationResponse>

    @GET("destination")
    suspend fun getAllDestinationPagination(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): DestinationResponse

    @GET("destination/detail/{id}")
    fun getDetailDestination(
        @Path("id") id: String,
    ): Call<DetailDestinationResponse>

    @GET("attraction")
    fun getAllAttraction(
    ): Call<AttractionResponse>

    @GET("attraction")
    suspend fun getAllAttractionPagination(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): AttractionResponse

    @GET("souvenir") //sudah include pagination
    fun getAllSouvenir(
    ): Call<SouvenirResponse>

    @GET("souvenir") //sudah include pagination
    suspend fun getAllSouvenirPagination(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): SouvenirResponse

    @FormUrlEncoded
    @POST("itenerary")
    fun postItenerary(
        @Field("destination") destination: String,
        @Field("duration") duration: Int,
        @Field("budget") budget: Int,
    ): Call<IteneraryResponse>

    @GET("itenerary/list")
    fun getSavedItenerary() : Call<IteneraryListResponse>

    @GET("itenerary/save/{id}")
    fun saveItenerary(
        @Path("id") id: String
    ) : Call<SaveIteneraryResponse>

    @GET("itenerary/delete/{id}")
    fun deleteItenerary(
        @Path("id") id: String
    ) : Call<DeleteIteneraryResponse>
}