package com.aman.wall.paper.base

import com.aman.wall.paper.network.ApiError

interface BasePhotoPresenterContract {
    fun onError(error: ApiError)
    fun getDownloadLink(id: String)
    fun onDownloadLinkSuccess(url: String)
}