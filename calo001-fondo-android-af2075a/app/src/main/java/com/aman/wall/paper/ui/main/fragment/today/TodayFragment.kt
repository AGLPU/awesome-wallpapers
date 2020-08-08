package com.aman.wall.paper.ui.main.fragment.today

import android.app.Activity
import com.aman.wall.paper.R
import com.aman.wall.paper.base.BasePhotoFragment
import com.aman.wall.paper.model.Photo
import com.aman.wall.paper.network.ApiError

class TodayFragment : BasePhotoFragment<TodayPresenterContract>(), TodayViewContract {

    override val presenter: TodayPresenterContract = TodayPresenterImpl(this)

    override fun onLoadPhotosSuccess(list: List<Photo>) {
        if (list.isEmpty() and (mPage == FIRST_PAGE)) {
            onError(ApiError(204, resources.getString(R.string.no_photos_found)))
        } else {
            mAdapter.removeProgressItem()
            mAdapter.addPage(list)
        }
    }

    override fun setupActivity(activity: Activity) {
        super.setupActivity(activity)
        presenter.loadPhotos(mPage)
    }

    override fun setupHeader() {
        mAdapter.addHeader(getString(R.string.today))
    }

    override fun loadPhotos() {
        presenter.loadPhotos(mPage)
    }

    companion object {
        const val TAG = "TodayFragment"

        @JvmStatic
        fun newInstance() = TodayFragment()
    }
}