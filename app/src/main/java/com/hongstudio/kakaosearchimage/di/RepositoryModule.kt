package com.hongstudio.kakaosearchimage.di

import com.hongstudio.kakaosearchimage.data.DefaultDocumentRepository
import com.hongstudio.kakaosearchimage.data.DocumentRepository
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
