package com.aman.wall.paper.ui.main.fragment.search

import com.aman.wall.paper.base.BasePhotoPresenterImpl
import com.aman.wall.paper.model.Result

class SearchPresenterImpl(override val view: SearchViewContract) :
    BasePhotoPresenterImpl<SearchViewContract, SearchInteractorContract>(view),
    SearchPresenterContract {

    override val interactor: SearchInteractorContract = SearchInteractorImpl(this)

    override fun loadPhotos(query: String, page: Int) {
        interactor.loadPhotos(query, page)
    }

    override fun onPhotosSuccess(result: Result) {
        view.hideLoading()
        view.onLoadPhotosSuccess(result)
    }
}