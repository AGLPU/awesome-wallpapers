package com.aman.wall.paper.ui.main.fragment.history

import com.aman.wall.paper.base.BasePhotoInteractorImpl
import com.aman.wall.paper.repository.HistoryRepository

class HistoryInteractorImpl (override val presenter: HistoryPresenterContract):
    BasePhotoInteractorImpl<HistoryPresenterContract>(presenter),
    HistoryInteractorContract {

    private val repo = HistoryRepository()

    override fun loadPhotos(page: Int) {
        presenter.onPhotosSuccess(repo.getHistory(page = page))
    }
}

