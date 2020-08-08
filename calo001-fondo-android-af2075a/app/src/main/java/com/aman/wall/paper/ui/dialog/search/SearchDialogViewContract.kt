package com.aman.wall.paper.ui.dialog.search

interface SearchDialogViewContract {
    fun onSuccess(list: List<String>)
    fun onError(error: String)
}