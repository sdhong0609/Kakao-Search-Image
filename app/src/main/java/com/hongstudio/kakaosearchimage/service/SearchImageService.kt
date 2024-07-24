package com.hongstudio.kakaosearchimage.service

import com.hongstudio.kakaosearchimage.model.GetSearchedImagesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchImageService {

    @GET("v2/search/image")
    suspend fun getSearchedImages(
        @Header("Authorization") authorization: String,
        @Query("query") query: String
    ): GetSearchedImagesResponse
}
