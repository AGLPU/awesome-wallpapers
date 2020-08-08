package com.aman.wall.paper.ui.main.fragment.search

import com.aman.wall.paper.base.BasePhotoViewContract
import com.aman.wall.paper.model.Result

interface SearchViewContract : BasePhotoViewContract {
    fun onLoadPhotosSuccess(result: Result)
}