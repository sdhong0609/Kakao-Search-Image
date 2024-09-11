package com.hongstudio.local.module.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): com.hongstudio.local.room.FavoriteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            com.hongstudio.local.room.FavoriteDatabase::class.java,
            "favorite-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(database: com.hongstudio.local.room.FavoriteDatabase): com.hongstudio.local.dao.FavoriteDao = database.favoriteDao()
}
