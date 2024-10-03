package com.hongstudio.data.datasource.remote

import com.hongstudio.data.model.DocumentDto

interface DocumentRemoteDataSource {

    suspend fun getSearchedImages(
        query: String,
        page: Int
    ): List<DocumentDto>
}
