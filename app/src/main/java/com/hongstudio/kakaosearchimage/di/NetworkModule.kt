package com.hongstudio.kakaosearchimage.di

import com.hongstudio.kakaosearchimage.BuildConfig
import com.hongstudio.kakaosearchimage.common.DefaultJson
import com.hongstudio.kakaosearchimage.data.source.network.SearchImageApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val contentType = "application/json".toMediaType()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(DefaultJson.asConverterFactory(contentType))
        .build()

    @Singleton
    @Provides
    fun provideSearchImageApi(retrofit: Retrofit): SearchImageApi {
        return retrofit.create(SearchImageApi::class.java)
    }
}
