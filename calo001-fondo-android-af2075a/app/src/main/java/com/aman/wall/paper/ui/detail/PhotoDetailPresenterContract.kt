package com.aman.wall.paper.ui.detail

import com.aman.wall.paper.network.ApiError

interface PhotoDetailPresenterContract {
    fun getDownloadLink(id: String)
    fun onDownloadLinkSuccess(image: String)
    fun onError(error: ApiError)
}
