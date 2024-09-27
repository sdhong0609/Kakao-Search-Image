package com.hongstudio.data.repository

import com.hongstudio.data.datasource.local.DocumentLocalDataSource
import com.hongstudio.data.datasource.remote.DocumentRemoteDataSource
import com.hongstudio.data.model.DocumentDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DocumentRepositoryImpl @Inject constructor(
    private val localDataSource: DocumentLocalDataSource,
    private val remoteDataSource: DocumentRemoteDataSource
) : DocumentRepository {

    override fun getAll(): Flow<List<DocumentDto>> {
        return localDataSource.getAll()
    }

    override suspend fun insert(documentDto: DocumentDto) {
        return localDataSource.insert(documentDto)
    }

    override suspend fun delete(documentDto: DocumentDto) {
        return localDataSource.delete(documentDto)
    }

    override suspend fun getSearchedImages(
        authorization: String,
        query: String,
        page: Int,
        size: Int
    ): List<DocumentDto> {
        return remoteDataSource.getSearchedImages(
            authorization = authorization,
            query = query,
            page = page,
            size = size
        )
    }
}
