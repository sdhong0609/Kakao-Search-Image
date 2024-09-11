package com.hongstudio.data

import com.hongstudio.data.source.local.FavoriteDao
import com.hongstudio.data.source.local.LocalDocument
import com.hongstudio.data.source.network.GetSearchedImagesResponse
import com.hongstudio.data.source.network.SearchImageApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultDocumentRepository @Inject constructor(
    private val favoriteLocalDataSource: FavoriteDao,
    private val documentRemoteDataSource: SearchImageApi
) : DocumentRepository {

    override fun getAll(): Flow<List<LocalDocument>> {
        return favoriteLocalDataSource.getAll()
    }

    override suspend fun insert(localDocument: LocalDocument) {
        return favoriteLocalDataSource.insert(localDocument)
    }

    override suspend fun delete(localDocument: LocalDocument) {
        return favoriteLocalDataSource.delete(localDocument)
    }

    override suspend fun getSearchedImages(
        authorization: String,
        query: String
    ): GetSearchedImagesResponse {
        return documentRemoteDataSource.getSearchedImages(
            authorization = authorization,
            query = query
        )
    }
}
