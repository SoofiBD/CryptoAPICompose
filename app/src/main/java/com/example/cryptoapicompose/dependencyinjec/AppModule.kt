package com.example.cryptoapicompose.dependencyinjec

import com.example.cryptoapicompose.repository.CryptoRepository
import com.example.cryptoapicompose.service.CryptoAPI
import com.example.cryptoapicompose.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

//97. eps 15:12
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCryptoRepository(api: CryptoAPI) : CryptoRepository {
        return CryptoRepository(api)
    }


    @Singleton
    @Provides
    fun provideCryptoApi() : CryptoAPI {
        return Retrofit.Builder()
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CryptoAPI::class.java)
    }
}