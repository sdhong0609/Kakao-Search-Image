package com.hongstudio.data.di

import com.hongstudio.data.DefaultDocumentRepository
import com.hongstudio.data.DocumentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindDocumentRepository(repository: DefaultDocumentRepository): DocumentRepository
}
