package com.hongstudio.remote.module.api

import com.hongstudio.remote.api.SearchImageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchApiModule {

    @Singleton
    @Provides
    fun provideSearchImageApi(retrofit: Retrofit): SearchImageApi {
        return retrofit.create(SearchImageApi::class.java)
    }
}
