package com.aman.wall.paper.ui.main.fragment.search

import android.annotation.SuppressLint
import com.aman.wall.paper.base.BasePhotoInteractorImpl
import com.aman.wall.paper.model.Result
import com.aman.wall.paper.network.ApiError
import com.aman.wall.paper.repository.UnsplashRepository

class SearchInteractorImpl(override val presenter: SearchPresenterContract) :
    BasePhotoInteractorImpl<SearchPresenterContract>(presenter), SearchInteractorContract {

    @SuppressLint("CheckResult")
    override fun loadPhotos(query: String, page: Int) {
        UnsplashRepository.getQueryPhotos(query, page)
            .subscribe({ result ->
                val listSorted = result.results.sortedWith(Comparator{a, b ->
                    val c = a.width / a.height
                    val d = b.width / b.height
                    c - d
                })
                presenter.onPhotosSuccess(Result(listSorted, result.total))
            }, { error ->
                val apiError = ApiError(error)
                presenter.onError(apiError)
            })
    }
}