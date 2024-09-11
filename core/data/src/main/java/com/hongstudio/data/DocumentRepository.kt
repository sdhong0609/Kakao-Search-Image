package com.hongstudio.data

import com.hongstudio.data.source.local.LocalDocument
import com.hongstudio.data.source.network.GetSearchedImagesResponse
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {

    fun getAll(): Flow<List<LocalDocument>>

    suspend fun insert(localDocument: LocalDocument)

    suspend fun delete(localDocument: LocalDocument)

    suspend fun getSearchedImages(
        authorization: String,
        query: String
    ): GetSearchedImagesResponse
}
