package com.wojtek.rx_java_mvvm_example.ui.main


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wojtek.rx_java_mvvm_example.data.googleBook.GoogleBook
import com.wojtek.rx_java_mvvm_example.data.googleBook.GoogleBookRepository
import com.wojtek.rx_java_mvvm_example.data.query.SearchQuery
import com.wojtek.rx_java_mvvm_example.data.query.SearchQueryRepository
import com.wojtek.rx_java_mvvm_example.utils.ResultEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainViewModel(
    private val bookRepository: GoogleBookRepository,
    private val searchQueryRepository: SearchQueryRepository
) : ViewModel() {


    var bookList: MutableLiveData<GoogleBook> = MutableLiveData()
    private var viewModelJob: Job? = null
    val resultLiveData = MutableLiveData<ResultEvent>()

    init {
        searchQuery("Aerodynamics", true)
    }

    private fun handleResult() {
        resultLiveData.value = ResultEvent.Success
    }

    private fun handleResult(message: String) {
        resultLiveData.value = ResultEvent.Error(message)
    }

    private suspend fun searchBooks(query: String) {
        val text = query.trim()
        if (text.isNotEmpty()) {
            val request = bookRepository.getBooksFromApi(query)
            try {
                if (request.isSuccessful) {
                    Log.d(TAG, "searchBooks: onSuccess")
                    handleResult()
                    bookRepository.updateGoogleBook(request.body()!!)
                    bookList.value = request.body()!!
                }
            } catch (_throwable: Exception) {
                Log.d(TAG, "searchBooks: Error")
                _throwable.message?.let {
                    handleResult(it)
                }
            }
        }
    }

    fun searchQuery(query: String, isFirst: Boolean) {

        viewModelJob?.cancel()
        viewModelJob = viewModelScope.launch {
            var text = query.trim()
            if (isFirst) {
                val searchQuery = searchQueryRepository.getSearchQuery()
                searchQuery?.let {
                    text = it.message
                    Log.d(TAG, "searchQuery: Query is $text")
                } ?: kotlin.run {
                    Log.d(TAG, "searchQuery: Query is empty")
                }
            }

            if (text.isNotEmpty()) {
                searchQueryRepository.updateSearchQuery(query = SearchQuery(message = text))
                searchBooks(text)
            }

            handleResult()
        }
    }
}