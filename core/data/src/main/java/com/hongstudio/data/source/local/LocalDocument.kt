package com.hongstudio.data.source.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.hongstudio.data.source.base.BaseViewHolderItem
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class LocalDocument(
    @PrimaryKey
    val thumbnailUrl: String,
    val imageUrl: String,
    val displaySitename: String,
    val docUrl: String,
    val datetimeString: String,
    val isFavorite: Boolean
) : Parcelable, BaseViewHolderItem {

    @IgnoredOnParcel
    @Ignore
    override val id: String = thumbnailUrl
}
