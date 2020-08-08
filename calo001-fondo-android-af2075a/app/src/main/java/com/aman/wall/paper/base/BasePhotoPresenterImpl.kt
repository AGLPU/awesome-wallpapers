package com.aman.wall.paper.base

import com.aman.wall.paper.network.ApiError

abstract class BasePhotoPresenterImpl<V: BasePhotoViewContract, I : BasePhotoInteractorContract>
    (open val view: V): BasePhotoPresenterContract {

    abstract val interactor: I

    override fun onError(error: ApiError) {
        view.onError(error)
    }

    override fun getDownloadLink(id: String) {
        interactor.getDownloadLink(id)
    }

    override fun onDownloadLinkSuccess(url: String) {
        view.onDownloadLinkSuccess(url)
    }
}