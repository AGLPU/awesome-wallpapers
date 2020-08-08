package com.aman.wall.paper.manager.history

import com.aman.wall.paper.model.Photo

class HistoryPresenterImpl (val manager: HistoryContract): HistoryPresenterContract {

    private val interactor = HistoryInteractorImpl(this)

    override fun addToHistory(photo: Photo) {
        interactor.addToHistory(photo)
    }

    override fun onHistorySuccess() {
        manager.onHistorySuccess()
    }

    override fun onHistoryError() {
        manager.onHistoryError()
    }
}