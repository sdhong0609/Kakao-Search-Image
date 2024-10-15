package com.hongstudio.core.domain.usecase

import com.hongstudio.core.domain.Document
import com.hongstudio.core.domain.repository.DocumentRepository
import javax.inject.Inject

class GetSearchedImagesUseCase @Inject constructor(
    private val documentRepository: DocumentRepository
) {
    suspend operator fun invoke(
        keyword: String,
        page: Int
    ): List<Document> = documentRepository.getSearchedImages(
        query = keyword,
        page = page
    )
}
