package com.aman.wall.paper.ui.main.fragment.search

import com.aman.wall.paper.R
import com.aman.wall.paper.base.BasePhotoFragment
import com.aman.wall.paper.model.Result
import com.aman.wall.paper.network.ApiError

class SearchFragment : BasePhotoFragment<SearchPresenterContract>(), SearchViewContract {
    override val presenter: SearchPresenterContract = SearchPresenterImpl(this)
    private var query = ""

    override fun onLoadPhotosSuccess(result: Result) {
        if (result.results.isEmpty() and (mPage == FIRST_PAGE)) {
            onError(ApiError(204, resources.getString(R.string.empty_search)))
        } else {
            mAdapter.removeProgressItem()
            mAdapter.addPage(result.results)
        }
    }

    override fun setupHeader() {
        mAdapter.addHeader(getString(R.string.search_header))
    }

    override fun loadPhotos() {
        presenter.loadPhotos(query, mPage)
    }

    fun newSearchQuery(newQuery: String) {
        cleanData()
        query = newQuery
        presenter.loadPhotos(query, mPage)
        mAdapter.updateHeader(query)
    }

    fun cleanData() {
        mPage = 1
        mAdapter.clear()
        showLoading()
    }

    companion object {
        const val TAG = "SearchFragment"

        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}
