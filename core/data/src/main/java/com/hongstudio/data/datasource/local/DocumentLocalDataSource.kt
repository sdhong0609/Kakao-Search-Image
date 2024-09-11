package com.hongstudio.data.datasource.local

import com.hongstudio.data.model.DocumentDto
import kotlinx.coroutines.flow.Flow

interface DocumentLocalDataSource {
    fun getAll(): Flow<List<DocumentDto>>
    suspend fun insert(documentDto: DocumentDto)
    suspend fun delete(documentDto: DocumentDto)
}
