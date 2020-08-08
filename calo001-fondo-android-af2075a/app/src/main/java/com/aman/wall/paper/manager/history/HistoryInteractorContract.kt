package com.aman.wall.paper.manager.history

import com.aman.wall.paper.model.Photo

interface HistoryInteractorContract {
    fun addToHistory(photo: Photo)
}