package com.wojtek.rx_java_mvvm_example.data.googleBook

import com.wojtek.rx_java_mvvm_example.api.GoogleBookApi

class GoogleBookRepository(
    private val api: GoogleBookApi,
    private val dao: GoogleBookDao
) {

    suspend fun getBooksFromApi(query: String) = api.getBooks(query)
    suspend fun getBooksFromDb() = dao.getGoogleBook()
    suspend fun insertGoogleBook(googleBook: GoogleBook) = dao.insertGoogleBook(googleBook)
    suspend fun deleteGoogleBook() = dao.deleteGoogleBook()
    suspend fun updateGoogleBook(googleBook: GoogleBook) = dao.updateGoogleBook(googleBook)
}