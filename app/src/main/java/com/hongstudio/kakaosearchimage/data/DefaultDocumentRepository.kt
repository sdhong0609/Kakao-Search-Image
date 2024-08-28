package com.hongstudio.kakaosearchimage.data

import com.hongstudio.kakaosearchimage.data.source.local.FavoriteDao
import com.hongstudio.kakaosearchimage.data.source.local.LocalDocument
import com.hongstudio.kakaosearchimage.data.source.network.GetSearchedImagesResponse
import com.hongstudio.kakaosearchimage.data.source.network.SearchImageApi
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
