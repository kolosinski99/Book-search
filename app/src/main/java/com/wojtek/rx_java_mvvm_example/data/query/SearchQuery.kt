package com.wojtek.rx_java_mvvm_example.data.query

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "search_query")
data class SearchQuery(
    @PrimaryKey(autoGenerate = true) @SerializedName("id") val id: Int = 0,
    @SerializedName("message") val message: String
)