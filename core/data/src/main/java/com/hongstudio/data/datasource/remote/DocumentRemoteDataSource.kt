package com.hongstudio.data.datasource.remote

import com.hongstudio.data.model.DocumentDto

interface DocumentRemoteDataSource {

    suspend fun getSearchedImages(
        authorization: String,
        query: String,
        page: Int,
        size: Int
    ): List<DocumentDto>
}
