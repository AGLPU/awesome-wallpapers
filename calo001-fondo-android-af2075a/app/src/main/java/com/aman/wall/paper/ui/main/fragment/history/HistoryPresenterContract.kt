package com.aman.wall.paper.ui.main.fragment.history

import com.aman.wall.paper.base.BasePhotoPresenterContract
import com.aman.wall.paper.model.Photo

interface HistoryPresenterContract : BasePhotoPresenterContract{
    fun loadPhotos(page: Int)
    fun onPhotosSuccess(list: List<Photo>)
}