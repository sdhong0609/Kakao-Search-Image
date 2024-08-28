package com.hongstudio.kakaosearchimage.data

import com.hongstudio.kakaosearchimage.data.source.local.LocalDocument
import com.hongstudio.kakaosearchimage.data.source.network.NetworkDocument

fun NetworkDocument.toLocal() = LocalDocument(
    thumbnailUrl = thumbnailUrl,
    imageUrl = imageUrl,
    displaySitename = displaySitename,
    docUrl = docUrl,
    datetimeString = datetime.toString(),
    isFavorite = false
)
