package com.aman.wall.paper.ui.main.fragment.history

import com.aman.wall.paper.base.BasePhotoInteractorContract

interface HistoryInteractorContract : BasePhotoInteractorContract{
    fun loadPhotos(page: Int)
}