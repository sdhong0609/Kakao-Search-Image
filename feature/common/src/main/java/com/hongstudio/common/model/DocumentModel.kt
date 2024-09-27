package com.hongstudio.common.model

import com.hongstudio.data.model.DocumentDto
import kotlinx.serialization.Serializable

@Serializable
data class DocumentModel(
    val thumbnailUrl: String,
    val imageUrl: String,
    val displaySitename: String,
    val docUrl: String,
    val datetimeString: String,
    val isFavorite: Boolean
) : DocumentListItem {

    override val id: String = thumbnailUrl
}

fun DocumentDto.toUiModel(): DocumentModel {
    return DocumentModel(
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl,
        displaySitename = displaySitename,
        docUrl = docUrl,
        datetimeString = datetimeString,
        isFavorite = isFavorite
    )
}

fun DocumentModel.toDto(): DocumentDto {
    return DocumentDto(
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl,
        displaySitename = displaySitename,
        docUrl = docUrl,
        datetimeString = datetimeString,
        isFavorite = isFavorite
    )
}
