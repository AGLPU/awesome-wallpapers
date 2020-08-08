package com.aman.wall.paper.ui.main.fragment.today

import com.aman.wall.paper.base.BasePhotoPresenterImpl
import com.aman.wall.paper.model.Photo

class TodayPresenterImpl (override val view: TodayViewContract) :
    BasePhotoPresenterImpl<TodayViewContract, TodayInteractorContract>(view),
    TodayPresenterContract {

    override val interactor: TodayInteractorContract = TodayInteractorImpl(this)

    override fun loadPhotos(page: Int) {
        interactor.loadPhotos(page)
    }

    override fun onPhotosSuccess(list: List<Photo>) {
        view.hideLoading()
        view.onLoadPhotosSuccess(list)
    }
}