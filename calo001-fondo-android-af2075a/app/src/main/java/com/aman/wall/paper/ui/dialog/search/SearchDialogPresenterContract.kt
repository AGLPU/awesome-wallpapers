package com.aman.wall.paper.ui.dialog.search

import com.aman.wall.paper.model.SearchHistory

interface SearchDialogPresenterContract {
    fun getHistory()
    fun onGetHistorySuccess(list: List<String>)
    fun onError(error: String)
    fun addSearchHistoryItem(search: SearchHistory)
    fun deleteFromHistory(value: String)
}