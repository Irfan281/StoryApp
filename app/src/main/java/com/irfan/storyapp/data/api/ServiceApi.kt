package com.irfan.storyapp.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ServiceApi {
    @FormUrlEncoded
    @POST("register")
    fun postDaftar(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<DaftarResponse>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") header: String,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("location") location: Int
    ): GetResponse

    @Multipart
    @POST("stories")
    fun postStories(
        @Header("Authorization")
        header: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Float?,
        @Part("lon") lon: Float?
    ): Call<UploadResponse>
}