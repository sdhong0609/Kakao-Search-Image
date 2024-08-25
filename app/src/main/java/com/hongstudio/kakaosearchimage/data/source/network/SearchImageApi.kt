package com.hongstudio.kakaosearchimage.data.source.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchImageApi {

    @GET("v2/search/image")
    suspend fun getSearchedImages(
        @Header("Authorization") authorization: String,
        @Query("query") query: String
    ): GetSearchedImagesResponse
}
