package com.hongstudio.kakaosearchimage.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSearchedImagesResponse(
    @SerialName("meta")
    val meta: Meta,
    @SerialName("documents")
    val documents: List<NetworkDocument>
)
