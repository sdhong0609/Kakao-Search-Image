package com.hongstudio.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.hongstudio.data.common.BaseViewHolderItem
import com.hongstudio.data.model.DocumentDto
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class DocumentLocal(
    @PrimaryKey
    val thumbnailUrl: String,
    val imageUrl: String,
    val displaySitename: String,
    val docUrl: String,
    val datetimeString: String,
    val isFavorite: Boolean
) : Parcelable, BaseViewHolderItem {

    @IgnoredOnParcel
    @Ignore
    override val id: String = thumbnailUrl
}

fun DocumentDto.toLocal() = DocumentLocal(
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    displaySitename = displaySitename,
    docUrl = docUrl,
    datetimeString = datetimeString,
    isFavorite = isFavorite
)

fun DocumentLocal.toDto() = DocumentDto(
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    displaySitename = displaySitename,
    docUrl = docUrl,
    datetimeString = datetimeString,
    isFavorite = isFavorite
)
