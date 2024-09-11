package com.hongstudio.data.di

import android.content.Context
import androidx.room.Room
import com.hongstudio.data.source.local.FavoriteDao
import com.hongstudio.data.source.local.FavoriteDatabase
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
    fun provideDataBase(@ApplicationContext context: Context): FavoriteDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FavoriteDatabase::class.java,
            "favorite-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(database: FavoriteDatabase): FavoriteDao = database.favoriteDao()
}
