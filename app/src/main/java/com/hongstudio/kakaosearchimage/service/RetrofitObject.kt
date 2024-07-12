package com.hongstudio.kakaosearchimage.service

import com.hongstudio.kakaosearchimage.BuildConfig
import com.hongstudio.kakaosearchimage.common.DefaultJson
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitObject {
    private val contentType = "application/json".toMediaType()
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(DefaultJson.asConverterFactory(contentType))
        .build()
    val searchImageService: SearchImageService = retrofit.create(SearchImageService::class.java)
}
