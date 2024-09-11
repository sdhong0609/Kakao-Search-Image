package com.hongstudio.local.datasource

import com.hongstudio.data.datasource.local.DocumentLocalDataSource
import com.hongstudio.local.dao.FavoriteDao
import com.hongstudio.local.model.DocumentLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DocumentLocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
): DocumentLocalDataSource {

    override fun getAll(): Flow<List<DocumentLocal>> {
        return favoriteDao.getAll()
    }

    override suspend fun insert(documentLocal: DocumentLocal) {
        return favoriteDao.insert(documentLocal)
    }

    override suspend fun delete(documentLocal: DocumentLocal) {
        return favoriteDao.delete(documentLocal)
    }
}
