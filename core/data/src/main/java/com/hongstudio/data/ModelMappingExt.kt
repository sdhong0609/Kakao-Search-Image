package com.hongstudio.data

import com.hongstudio.data.source.local.LocalDocument
import com.hongstudio.data.source.network.NetworkDocument

fun NetworkDocument.toLocal() = LocalDocument(
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    displaySitename = displaySitename,
    docUrl = docUrl,
    datetimeString = datetime.toString(),
    isFavorite = false
)
