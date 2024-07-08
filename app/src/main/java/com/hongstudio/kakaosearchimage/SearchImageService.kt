package com.hongstudio.kakaosearchimage

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchImageService {

    @GET("v2/search/image")
    fun getSearchedImages(
        @Header("Authorization") authorization: String,
        @Query("query") query: String
    ): Call<GetSearchedImagesResponse>
}
