package com.hongstudio.kakaosearchimage.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalDocument::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}
