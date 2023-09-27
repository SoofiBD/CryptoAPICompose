package com.example.cryptoapicompose.repository

import com.example.cryptoapicompose.model.Crypto
import com.example.cryptoapicompose.model.CryptoList
import com.example.cryptoapicompose.service.CryptoAPI
import com.example.cryptoapicompose.util.Constants.API_KEY
import com.example.cryptoapicompose.util.Constants.CALL_ATTRIBUTES
import com.example.cryptoapicompose.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val api: CryptoAPI
) {
    suspend fun getCryptoList() : Resource<CryptoList> {
        val response = try {
            api.getCryptoList(API_KEY)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto (id: String) : Resource<Crypto> {
        val response = try {
            api.getCryptoDetail(API_KEY, id, CALL_ATTRIBUTES)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occured")
        }
        return Resource.Success(response)
    }
}