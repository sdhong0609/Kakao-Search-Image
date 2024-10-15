package com.hongstudio.core.domain.usecase

import com.hongstudio.core.domain.Document
import com.hongstudio.core.domain.repository.DocumentRepository
import javax.inject.Inject

class DeleteDocumentUseCase @Inject constructor(
    private val documentRepository: DocumentRepository
) {
    suspend operator fun invoke(document: Document) = documentRepository.delete(document)
}
