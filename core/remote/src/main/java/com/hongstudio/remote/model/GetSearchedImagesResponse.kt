package com.hongstudio.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSearchedImagesResponse(
    @SerialName("meta")
    val meta: Meta,
    @SerialName("documents")
    val documents: List<DocumentRemote>
)
