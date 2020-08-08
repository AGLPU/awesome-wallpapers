package com.aman.wall.paper.ui.detail

import android.annotation.SuppressLint
import com.aman.wall.paper.network.ApiError
import com.aman.wall.paper.repository.UnsplashRepository

class PhotoDetailInteractorImpl(private val presenter: PhotoDetailPresenterContract):
    PhotoDetailInteractorContract {

    @SuppressLint("CheckResult")
    override fun getDownloadLink(id: String) {
        UnsplashRepository.getDownloadLinkLocation(id)
            .subscribe({ response ->
                presenter.onDownloadLinkSuccess(response.url)
            }, { error ->
                val errorApi = ApiError(error)
                presenter.onError(errorApi)
            })
    }
}