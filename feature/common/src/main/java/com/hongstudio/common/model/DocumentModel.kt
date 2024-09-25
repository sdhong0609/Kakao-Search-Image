package com.hongstudio.common.model

import android.os.Parcelable
import com.hongstudio.data.model.DocumentDto
import com.hongstudio.ui.base.BaseViewHolderItem
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentModel(
    val thumbnailUrl: String,
    val imageUrl: String,
    val displaySitename: String,
    val docUrl: String,
    val datetimeString: String,
    val isFavorite: Boolean
) : Parcelable, BaseViewHolderItem {

    @IgnoredOnParcel
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
