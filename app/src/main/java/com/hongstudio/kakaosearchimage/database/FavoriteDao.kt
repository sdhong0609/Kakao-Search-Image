package com.hongstudio.kakaosearchimage.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM documententity")
    suspend fun getAll(): List<DocumentEntity>

    @Insert
    suspend fun insert(documentEntity: DocumentEntity)

    @Delete
    suspend fun delete(documentEntity: DocumentEntity)
}
