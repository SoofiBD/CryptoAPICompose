package com.example.cryptoapicompose.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptoapicompose.model.Crypto
import com.example.cryptoapicompose.repository.CryptoRepository
import com.example.cryptoapicompose.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptodetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {
    suspend fun getCrypto(id: String) : Resource<Crypto> {
        return repository.getCrypto(id)
    }
}