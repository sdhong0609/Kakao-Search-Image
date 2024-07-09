package com.hongstudio.kakaosearchimage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DocumentDao {
    @Query("SELECT * FROM document")
    fun getAll(): List<Document>

    @Insert
    fun insert(document: Document)

    @Delete
    fun delete(document: Document)
}
