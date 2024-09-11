package com.hongstudio.data.module

import com.hongstudio.data.repository.DocumentRepository
import com.hongstudio.data.repository.DocumentRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DocumentRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindDocumentRepository(repository: DocumentRepositoryImpl): DocumentRepository
}
