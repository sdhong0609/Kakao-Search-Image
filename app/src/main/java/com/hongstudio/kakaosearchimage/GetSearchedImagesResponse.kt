package com.hongstudio.kakaosearchimage

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
data class Document(
    @SerialName("thumbnail_url")
    val thumbnailUrl: String,
    @SerialName("display_sitename")
    val displaySitename: String,
    @Transient
    var isFavorite: Boolean = false
)
