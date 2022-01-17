package com.wojtek.rx_java_mvvm_example.api

import com.wojtek.rx_java_mvvm_example.data.googleBook.GoogleBook
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBookApi {

    companion object {
        const val BASE_URL = "https://www.googleapis.com/books/v1/"
    }


    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String): Response<GoogleBook>
}