package com.aman.wall.paper.ui.main.fragment.search

import com.aman.wall.paper.base.BasePhotoInteractorContract

interface SearchInteractorContract : BasePhotoInteractorContract {
    fun loadPhotos(query: String, page: Int)
}