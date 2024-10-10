package com.hongstudio.data.repository

import com.hongstudio.core.domain.Document
import com.hongstudio.core.domain.repository.DocumentRepository
import com.hongstudio.data.datasource.local.DocumentLocalDataSource
import com.hongstudio.data.datasource.remote.DocumentRemoteDataSource
import com.hongstudio.data.model.toDomains
import com.hongstudio.data.model.toDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DocumentRepositoryImpl @Inject constructor(
    private val localDataSource: DocumentLocalDataSource,
    private val remoteDataSource: DocumentRemoteDataSource
) : DocumentRepository {

    override fun getAll(): Flow<List<Document>> {
        return localDataSource.getAll().map { it.toDomains() }
    }

    override suspend fun insert(document: Document) {
        return localDataSource.insert(document.toDto())
    }

    override suspend fun delete(document: Document) {
        return localDataSource.delete(document.toDto())
    }

    override suspend fun getSearchedImages(
        query: String,
        page: Int
    ): List<Document> {
        return remoteDataSource.getSearchedImages(
            query = query,
            page = page
        ).toDomains()
    }
}
