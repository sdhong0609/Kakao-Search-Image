package com.hongstudio.data.source.network

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkDocument(
    @SerialName("thumbnail_url")
    val thumbnailUrl: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("display_sitename")
    val displaySitename: String,
    @SerialName("doc_url")
    val docUrl: String,
    @SerialName("datetime")
    val datetime: Instant,
)
