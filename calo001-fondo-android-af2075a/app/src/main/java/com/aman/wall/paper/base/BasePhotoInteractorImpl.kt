package com.aman.wall.paper.base

import android.annotation.SuppressLint
import com.aman.wall.paper.network.ApiError
import com.aman.wall.paper.repository.UnsplashRepository

abstract class BasePhotoInteractorImpl<P : BasePhotoPresenterContract>(open val presenter: P) :
    BasePhotoInteractorContract {
    @SuppressLint("CheckResult")
    override fun getDownloadLink(id: String) {
        UnsplashRepository.getDownloadLinkLocation(id)
            .subscribe({ response ->
                presenter.onDownloadLinkSuccess(response.url)
            }, { error ->
                val apiError = ApiError(error)
                presenter.onError(apiError)
            })
    }
}