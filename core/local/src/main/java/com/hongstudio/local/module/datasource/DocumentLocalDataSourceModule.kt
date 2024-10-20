package com.hongstudio.local.module.datasource

import com.hongstudio.data.datasource.local.DocumentLocalDataSource
import com.hongstudio.local.datasource.DocumentLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DocumentLocalDataSourceModule {

    @Singleton
    @Binds
    fun bindDocumentLocalDataSource(impl: DocumentLocalDataSourceImpl): DocumentLocalDataSource
}
