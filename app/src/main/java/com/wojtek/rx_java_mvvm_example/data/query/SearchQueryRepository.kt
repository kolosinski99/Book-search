package com.wojtek.rx_java_mvvm_example.data.query

class SearchQueryRepository(
    private val dao: SearchQueryDao
) {
    suspend fun insertSearchQuery(query: SearchQuery) = dao.insertSearchQuery(query)
    suspend fun deleteSearchQuery() = dao.deleteSearchQuery()
    suspend fun updateSearchQuery(query: SearchQuery) = dao.updateSearchQuery(query)
    suspend fun getSearchQuery() = dao.getSearchQuery()
}