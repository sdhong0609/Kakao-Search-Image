package com.hongstudio.core.domain.usecase

import com.hongstudio.core.domain.Document
import com.hongstudio.core.domain.repository.DocumentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedDocumentsUseCase @Inject constructor(
    private val documentRepository: DocumentRepository
) {
    operator fun invoke(): Flow<List<Document>> = documentRepository.getAll()
}
