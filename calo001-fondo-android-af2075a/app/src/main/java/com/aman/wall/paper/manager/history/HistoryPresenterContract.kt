package com.aman.wall.paper.manager.history

import com.aman.wall.paper.model.Photo

interface HistoryPresenterContract {
    fun addToHistory(photo: Photo)
    fun onHistorySuccess()
    fun onHistoryError()
}