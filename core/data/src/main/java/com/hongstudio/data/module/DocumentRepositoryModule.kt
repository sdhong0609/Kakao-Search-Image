package com.hongstudio.data.module

import com.hongstudio.core.domain.repository.DocumentRepository
import com.hongstudio.data.repository.DocumentRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DocumentRepositoryModule {

    @Singleton
    @Binds
    fun bindDocumentRepository(repository: DocumentRepositoryImpl): DocumentRepository
}
