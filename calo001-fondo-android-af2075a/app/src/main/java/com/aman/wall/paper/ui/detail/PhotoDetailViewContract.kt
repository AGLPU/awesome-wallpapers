package com.aman.wall.paper.ui.detail

import com.aman.wall.paper.network.ApiError

interface PhotoDetailViewContract {
    fun onSuccess(image: String)
    fun onError(error: ApiError)
}