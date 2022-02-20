package com.example.beskorsravniosago.network


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://mock.sravni-team.ru/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService{
    @POST("mobile/internship/v1/osago/rationDetail")
    fun pushPost(
    ):Call<Factors>
}

object Api{
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
