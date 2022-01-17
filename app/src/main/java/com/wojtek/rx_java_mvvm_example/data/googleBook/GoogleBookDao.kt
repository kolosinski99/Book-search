package com.wojtek.rx_java_mvvm_example.data.googleBook

import androidx.room.*
import org.jetbrains.annotations.NotNull

@Dao
interface GoogleBookDao {
    @NotNull
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoogleBook(googleBook: GoogleBook)

    @Transaction
    suspend fun updateGoogleBook(googleBook: GoogleBook) {
        deleteGoogleBook()
        insertGoogleBook(googleBook)
    }

    @Query("DELETE FROM google_book")
    suspend fun deleteGoogleBook()

    @Query("SELECT * FROM google_book")
    suspend fun getGoogleBook(): GoogleBook?
}