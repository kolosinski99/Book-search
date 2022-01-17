package com.wojtek.rx_java_mvvm_example.data.query

import androidx.room.*
import org.jetbrains.annotations.NotNull

@Dao
interface SearchQueryDao {

    @NotNull
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchQuery(searchQuery: SearchQuery)

    @Query("SELECT * FROM search_query")
    suspend fun getSearchQuery(): SearchQuery?

    @Update
    suspend fun updateSearchQuery(searchQuery: SearchQuery)

    @Query("DELETE FROM search_query")
    suspend fun deleteSearchQuery()
}