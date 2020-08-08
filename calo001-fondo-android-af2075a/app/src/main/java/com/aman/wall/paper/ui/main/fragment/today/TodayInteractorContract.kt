package com.aman.wall.paper.ui.main.fragment.today

import com.aman.wall.paper.base.BasePhotoInteractorContract

interface TodayInteractorContract : BasePhotoInteractorContract {
    fun loadPhotos(page: Int)
}