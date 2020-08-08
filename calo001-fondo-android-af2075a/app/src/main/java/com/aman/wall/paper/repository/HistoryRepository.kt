package com.aman.wall.paper.repository

import com.aman.wall.paper.manager.json.FondoJsonManager
import com.aman.wall.paper.model.Photo

class HistoryRepository {
    private val jsonManager = FondoJsonManager()

    fun getHistory(page: Int): List<Photo> {
        return jsonManager.getHistory(page)
    }

    fun saveToHistory(photo: Photo): Boolean {
        return jsonManager.addToHistory(photo)
    }
}