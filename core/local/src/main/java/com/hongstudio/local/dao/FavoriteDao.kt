package com.hongstudio.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hongstudio.local.model.DocumentLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM DocumentLocal")
    fun getAll(): Flow<List<DocumentLocal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(documentLocal: DocumentLocal)

    @Delete
    suspend fun delete(documentLocal: DocumentLocal)
}
