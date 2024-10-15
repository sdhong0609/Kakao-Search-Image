package com.hongstudio.core.domain.repository

import com.hongstudio.core.domain.Document
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {

    fun getAll(): Flow<List<Document>>

    suspend fun insert(document: Document)

    suspend fun delete(document: Document)

    suspend fun getSearchedImages(
        query: String,
        page: Int
    ): List<Document>
}
