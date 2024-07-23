package com.hongstudio.kakaosearchimage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hongstudio.kakaosearchimage.model.Document.DocumentEntity

@Database(entities = [DocumentEntity::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun documentDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        fun getDatabase(context: Context): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favorite-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
