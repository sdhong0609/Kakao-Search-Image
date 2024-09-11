package com.hongstudio.data.repository

import com.hongstudio.data.model.DocumentDto
import com.hongstudio.data.source.network.GetSearchedImagesResponse
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {

    fun getAll(): Flow<List<DocumentDto>>

    suspend fun insert(documentDto: DocumentDto)

    suspend fun delete(documentDto: DocumentDto)

    suspend fun getSearchedImages(
        authorization: String,
        query: String
    ): GetSearchedImagesResponse
}
