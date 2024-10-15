package com.hongstudio.data.model

import com.hongstudio.core.domain.Document

data class DocumentDto(
    val thumbnailUrl: String,
    val imageUrl: String,
    val displaySitename: String,
    val docUrl: String,
    val datetimeString: String
)

internal fun Document.toDto() = DocumentDto(
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    displaySitename = displaySitename,
    docUrl = docUrl,
    datetimeString = datetimeString
)

internal fun DocumentDto.toDomain() = Document(
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    displaySitename = displaySitename,
    docUrl = docUrl,
    datetimeString = datetimeString
)

internal fun List<DocumentDto>.toDomains(): List<Document> = this.map { it.toDomain() }
