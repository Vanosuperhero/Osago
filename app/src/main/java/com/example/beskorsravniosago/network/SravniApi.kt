package com.example.beskorsravniosago.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
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

interface SravniApi{
    @POST("mobile/internship/v1/osago/rationDetail")
    suspend fun pushPost(
        @Body post: InputCoefs
    ):Response<Factors>
}

object Api{
    val retrofitApi: SravniApi by lazy {
        retrofit.create(SravniApi::class.java)
    }
}

interface OffersApi{
    @POST("mobile/internship/v1/osago/startCalculation")
    suspend fun pushPost(
        @Body post: Factors
    ):Response<Offers>
}

object ApiOffer{
    val retrofitApi: OffersApi by lazy {
        retrofit.create(OffersApi::class.java)
    }
}