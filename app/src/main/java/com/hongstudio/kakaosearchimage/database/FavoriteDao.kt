package com.hongstudio.kakaosearchimage.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM documententity")
    fun getAll(): Flow<List<DocumentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(documentEntity: DocumentEntity)

    @Delete
    suspend fun delete(documentEntity: DocumentEntity)
}
