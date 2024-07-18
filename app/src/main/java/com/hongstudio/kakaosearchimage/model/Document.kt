package com.hongstudio.kakaosearchimage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

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
    val isFavorite: Boolean = false
)
