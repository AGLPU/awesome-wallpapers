package com.aman.wall.paper.base

import com.aman.wall.paper.network.ApiError

interface BasePhotoViewContract {
    fun showLoading()
    fun hideLoading()
    fun onError(error: ApiError)
    fun onDownloadLinkSuccess(url: String)
}