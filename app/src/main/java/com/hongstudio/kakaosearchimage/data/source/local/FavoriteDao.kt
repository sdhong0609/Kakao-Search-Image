package com.hongstudio.kakaosearchimage.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM LocalDocument")
    fun getAll(): Flow<List<LocalDocument>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localDocument: LocalDocument)

    @Delete
    suspend fun delete(localDocument: LocalDocument)
}
