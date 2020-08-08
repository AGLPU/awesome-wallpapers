package com.aman.wall.paper.ui.dialog.search

import com.aman.wall.paper.model.SearchHistory

interface SearchDialogInteractorContract {
    fun getHistory()
    fun insert(search: SearchHistory)
    fun update(search: SearchHistory)
    fun delete(search: String)
}