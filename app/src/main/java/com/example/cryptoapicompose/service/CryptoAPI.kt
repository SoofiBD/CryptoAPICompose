package com.example.cryptoapicompose.service

import com.example.cryptoapicompose.model.Crypto
import com.example.cryptoapicompose.model.CryptoList
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoAPI {
    //prices?key=ff6c23229a2ae7afObe1f337c19451bbOe5cafOb
    @GET("prices")
    suspend fun getCryptoList(
        @Query("key") key: String

    ): CryptoList

    @GET("currencies")
    suspend fun getCryptoDetail(
        @Query("key") key: String,
        @Query("ids") ids: String,
        @Query("attributes") attributes: String
    ): Crypto
}