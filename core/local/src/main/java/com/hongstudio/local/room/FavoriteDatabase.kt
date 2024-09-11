package com.hongstudio.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hongstudio.local.model.DocumentLocal

@Database(entities = [DocumentLocal::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): com.hongstudio.local.dao.FavoriteDao
}
