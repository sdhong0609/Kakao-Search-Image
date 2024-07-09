package com.hongstudio.kakaosearchimage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Document::class], version = 1)
abstract class DocumentDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao

    companion object {
        fun getDatabase(context: Context): DocumentDatabase =
            Room.databaseBuilder(context, DocumentDatabase::class.java, "document-database").build()
    }
}
