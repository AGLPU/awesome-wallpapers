package com.aman.wall.paper.ui.main.fragment.today

import com.aman.wall.paper.base.BasePhotoPresenterContract
import com.aman.wall.paper.model.Photo

interface TodayPresenterContract: BasePhotoPresenterContract {
    fun loadPhotos(page: Int)
    fun onPhotosSuccess(list: List<Photo>)
}