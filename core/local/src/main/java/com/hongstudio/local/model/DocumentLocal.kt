package com.hongstudio.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hongstudio.data.model.DocumentDto

@Entity
data class DocumentLocal(
    @PrimaryKey
    val thumbnailUrl: String,
    val imageUrl: String,
    val displaySitename: String,
    val docUrl: String,
    val datetimeString: String
)

fun DocumentDto.toLocal() = DocumentLocal(
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    displaySitename = displaySitename,
    docUrl = docUrl,
    datetimeString = datetimeString
)

fun DocumentLocal.toDto() = DocumentDto(
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    displaySitename = displaySitename,
    docUrl = docUrl,
    datetimeString = datetimeString
)
