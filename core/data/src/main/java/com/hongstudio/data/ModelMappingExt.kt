package com.hongstudio.data

import com.hongstudio.local.model.LocalDocument
import com.hongstudio.data.source.network.NetworkDocument

fun NetworkDocument.toLocal() = com.hongstudio.local.model.LocalDocument(
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    displaySitename = displaySitename,
    docUrl = docUrl,
    datetimeString = datetime.toString(),
    isFavorite = false
)
