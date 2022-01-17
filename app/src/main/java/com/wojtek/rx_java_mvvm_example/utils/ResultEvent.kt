package com.wojtek.rx_java_mvvm_example.utils

sealed class ResultEvent {
    object Success : ResultEvent()
    data class Error(val message: String) : ResultEvent()
}
