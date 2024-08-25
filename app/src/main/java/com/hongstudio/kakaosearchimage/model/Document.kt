package com.hongstudio.kakaosearchimage.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hongstudio.kakaosearchimage.base.BaseViewHolderItem
import kotlinx.datetime.Instant
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Document(
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
) {
    fun toEntity(): DocumentEntity {
        return DocumentEntity(
            thumbnailUrl = thumbnailUrl,
            imageUrl = imageUrl,
            displaySitename = displaySitename,
            docUrl = docUrl,
            datetimeString = datetime.toString(),
            isFavorite = false
        )
    }

    @Parcelize
    @Entity
    data class DocumentEntity(
        @PrimaryKey
        val thumbnailUrl: String,
        val imageUrl: String,
        val displaySitename: String,
        val docUrl: String,
        val datetimeString: String,
        val isFavorite: Boolean
    ) : Parcelable, BaseViewHolderItem {
        @IgnoredOnParcel
        override val id: String = thumbnailUrl
    }
}
