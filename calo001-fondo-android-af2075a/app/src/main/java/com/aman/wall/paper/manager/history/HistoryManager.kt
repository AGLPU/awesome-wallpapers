package com.aman.wall.paper.manager.history

import android.util.Log
import com.aman.wall.paper.model.Photo

class HistoryManager : HistoryContract {
    private val historyPresenter: HistoryPresenterContract = HistoryPresenterImpl(this)

    fun addToHistory(photo: Photo) {
        historyPresenter.addToHistory(photo)
    }

    override fun onHistorySuccess() {
        Log.d(TAG, "History success")
    }

    override fun onHistoryError() {
        Log.d(TAG, "History error")
    }

    companion object {
        const val TAG = "HistoryManager"
    }
}