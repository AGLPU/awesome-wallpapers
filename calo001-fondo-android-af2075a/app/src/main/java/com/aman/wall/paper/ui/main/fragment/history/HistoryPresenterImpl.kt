package com.aman.wall.paper.ui.main.fragment.history

import com.aman.wall.paper.base.BasePhotoPresenterImpl
import com.aman.wall.paper.model.Photo

class HistoryPresenterImpl (override val view: HistoryViewContract) : BasePhotoPresenterImpl<HistoryViewContract, HistoryInteractorContract>(view),
    HistoryPresenterContract {

    override val interactor: HistoryInteractorContract = HistoryInteractorImpl(this)

    override fun loadPhotos(page: Int) {
        interactor.loadPhotos(page)
    }

    override fun onPhotosSuccess(list: List<Photo>) {
        view.hideLoading()
        view.onLoadPhotosSuccess(list)
    }
}