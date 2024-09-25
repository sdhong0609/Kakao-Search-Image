package com.hongstudio.remote.module.datasource

import com.hongstudio.data.datasource.remote.DocumentRemoteDataSource
import com.hongstudio.remote.datasource.DocumentRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DocumentRemoteDataSourceModule {

    @Singleton
    @Binds
    fun bindDocumentRemoteDataSource(impl: DocumentRemoteDataSourceImpl): DocumentRemoteDataSource
}
