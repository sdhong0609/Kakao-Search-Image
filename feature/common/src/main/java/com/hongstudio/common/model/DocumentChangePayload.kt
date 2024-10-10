package com.hongstudio.common.model

internal sealed interface DocumentChangePayload {
    data class Favorite(val item: DocumentModel) : DocumentChangePayload
}
