package com.aman.wall.paper.ui.main.fragment.history

import com.aman.wall.paper.base.BasePhotoViewContract
import com.aman.wall.paper.model.Photo

interface HistoryViewContract : BasePhotoViewContract {
    fun onLoadPhotosSuccess(list: List<Photo>)
}