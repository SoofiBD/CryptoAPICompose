package com.example.cryptoapicompose.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapicompose.model.CryptoListItem
import com.example.cryptoapicompose.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initialCryptoList = listOf<CryptoListItem>()
    private var isSearchStarting = true

    init {
        loadCrypto()
    }

    fun searchCryptoList(query: String) {
        val listToSearch = if (isSearchStarting) {
            cryptoList.value
        } else {
            initialCryptoList
        }

        viewModelScope.launch {
            if(query.isEmpty()) {
                cryptoList.value = initialCryptoList
                isSearchStarting = true
                return@launch
            }

            val results = listToSearch.filter {
                it.currency.contains(query.trim(), ignoreCase = true) ||
                        it.price.contains(query.trim(), ignoreCase = true)
            }

            if(isSearchStarting) {
                initialCryptoList = cryptoList.value
                isSearchStarting = false
            }
            cryptoList.value = results
        }
    }

    fun loadCrypto() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCryptoList()
            when(result) {
                is com.example.cryptoapicompose.util.Resource.Success -> {
                    val cryptoItems = result.data!!.mapIndexed { index, dataItem ->
                        CryptoListItem(dataItem.currency, dataItem.price)
                    }
                    cryptoList.value += cryptoItems
                    errorMessage.value = ""
                    isLoading.value = false
                }

                is com.example.cryptoapicompose.util.Resource.Error -> {
                    errorMessage.value = result.message!!
                    isLoading.value = false
                }

                else -> {
                    errorMessage.value = "An unexpected error occured"
                    isLoading.value = false
                }
            }
        }
    }
}