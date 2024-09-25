package com.hongstudio.local.datasource

import com.hongstudio.data.datasource.local.DocumentLocalDataSource
import com.hongstudio.data.model.DocumentDto
import com.hongstudio.local.dao.FavoriteDao
import com.hongstudio.local.model.toDto
import com.hongstudio.local.model.toLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DocumentLocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : DocumentLocalDataSource {

    override fun getAll(): Flow<List<DocumentDto>> {
        return favoriteDao.getAll().map {
            it.map { documentLocal ->
                documentLocal.toDto()
            }
        }
    }

    override suspend fun insert(documentDto: DocumentDto) {
        return favoriteDao.insert(documentDto.toLocal())
    }

    override suspend fun delete(documentDto: DocumentDto) {
        return favoriteDao.delete(documentDto.toLocal())
    }
}
