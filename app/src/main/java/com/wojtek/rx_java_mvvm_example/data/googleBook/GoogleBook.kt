package com.wojtek.rx_java_mvvm_example.data.googleBook

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "google_book")
data class GoogleBook(
    @PrimaryKey(autoGenerate = true) @SerializedName("id") val id: Int = 0,
    @SerializedName("items") val items: ArrayList<VolumeInfo>
)

data class VolumeInfo(
    @SerializedName("volumeInfo") val bookInfo: BookInfo
) {
    data class BookInfo(
        @SerializedName("title") val title: String,
        @SerializedName("publisher") val publisher: String,
        @SerializedName("description") val description: String,
        @SerializedName("imageLinks") val imageLinks: ImageLinks?
    )

    data class ImageLinks(
        @SerializedName("smallThumbnail") val smallThumbnail: String?
    )
}

