package com.wojtek.rx_java_mvvm_example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wojtek.rx_java_mvvm_example.data.googleBook.GoogleBook
import com.wojtek.rx_java_mvvm_example.data.googleBook.GoogleBookDao
import com.wojtek.rx_java_mvvm_example.data.query.SearchQuery
import com.wojtek.rx_java_mvvm_example.data.query.SearchQueryDao
import com.wojtek.rx_java_mvvm_example.utils.DataConverter

@TypeConverters(DataConverter::class)
@Database(
    entities = [
        GoogleBook::class,
        SearchQuery::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun googleBookDao(): GoogleBookDao
    abstract fun searchQueryDao(): SearchQueryDao
}