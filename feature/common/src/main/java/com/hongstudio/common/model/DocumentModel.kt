package com.hongstudio.common.model

import com.hongstudio.core.domain.Document
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

fun Document.toUiModel(): DocumentModel {
    return DocumentModel(
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl,
        displaySitename = displaySitename,
        docUrl = docUrl,
        datetimeString = datetimeString,
        isFavorite = false
    )
}

fun DocumentModel.toDomain(): Document {
    return Document(
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl,
        displaySitename = displaySitename,
        docUrl = docUrl,
        datetimeString = datetimeString
    )
}
