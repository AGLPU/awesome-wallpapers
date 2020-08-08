package com.aman.wall.paper.ui.main.fragment.history

import com.aman.wall.paper.R
import com.aman.wall.paper.base.BasePhotoFragment
import com.aman.wall.paper.model.Photo
import com.aman.wall.paper.network.ApiError

class HistoryFragment : BasePhotoFragment<HistoryPresenterContract>(), HistoryViewContract {
    override val presenter: HistoryPresenterContract = HistoryPresenterImpl(this)

    override fun onLoadPhotosSuccess(list: List<Photo>) {
        if (list.isEmpty() and (mPage == FIRST_PAGE)) {
            onError(ApiError(205, resources.getString(R.string.empty_history)))
        } else {
            mAdapter.removeProgressItem()
            mAdapter.addPage(list)
        }
    }

    override fun setupHeader() {
        mAdapter.addHeader(getString(R.string.history))
    }

    override fun loadPhotos() {
        presenter.loadPhotos(mPage)
    }

    fun reloadHistory() {
        cleanData()
        presenter.loadPhotos(mPage)
    }

    private fun cleanData() {
        mPage = 1
        mAdapter.clearOnlyData()
    }

    companion object {
        const val TAG = "HistoryFragment"

        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}