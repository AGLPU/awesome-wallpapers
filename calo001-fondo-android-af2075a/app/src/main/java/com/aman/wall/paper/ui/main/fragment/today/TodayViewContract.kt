package com.aman.wall.paper.ui.main.fragment.today

import com.aman.wall.paper.base.BasePhotoViewContract
import com.aman.wall.paper.model.Photo

interface TodayViewContract: BasePhotoViewContract {
    fun onLoadPhotosSuccess(list: List<Photo>)
}