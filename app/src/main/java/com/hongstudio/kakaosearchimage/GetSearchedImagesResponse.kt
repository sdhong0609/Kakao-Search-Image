package com.hongstudio.kakaosearchimage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class GetSearchedImagesResponse(
    @SerialName("meta")
    val meta: Meta,
    @SerialName("documents")
    val documents: List<Document>
)

@Serializable
data class Meta(
    @SerialName("is_end")
    val isEnd: Boolean
)

@Serializable
@Entity
data class Document(
    @SerialName("thumbnail_url")
    @PrimaryKey
    val thumbnailUrl: String,
    @SerialName("display_sitename")
    @ColumnInfo("display_sitename")
    val displaySitename: String,
    @Transient
    var isFavorite: Boolean = false
)
