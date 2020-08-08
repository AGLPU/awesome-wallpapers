package com.aman.wall.paper.manager.history

import com.aman.wall.paper.model.Photo
import com.aman.wall.paper.repository.HistoryRepository

class HistoryInteractorImpl(val presenter: HistoryPresenterContract) : HistoryInteractorContract {

    override fun addToHistory(photo: Photo) {
        val repo = HistoryRepository()
        val result = repo.saveToHistory(photo)
        if (result) {
            presenter.onHistorySuccess()
        } else {
            presenter.onHistoryError()
        }
    }
}