package com.aman.wall.paper.ui.main.fragment.search

import com.aman.wall.paper.base.BasePhotoPresenterContract
import com.aman.wall.paper.model.Result

interface SearchPresenterContract: BasePhotoPresenterContract {
    fun loadPhotos(query: String, page: Int)
    fun onPhotosSuccess(result: Result)
}