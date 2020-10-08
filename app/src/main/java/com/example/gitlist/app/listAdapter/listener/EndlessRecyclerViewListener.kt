package com.example.gitlist.app.listAdapter.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerViewListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var currentPage = 0
    private var previousTotalItems = 0
    private var loading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val lastVisiblePosition = this.layoutManager.findLastVisibleItemPosition()
        val totalItemCount = this.layoutManager.itemCount

        if (totalItemCount < previousTotalItems) {
            currentPage = STARTING_PAGE_INDEX
            previousTotalItems = totalItemCount
            if (totalItemCount == 0)
                loading = true
        }

        if (loading && (totalItemCount > previousTotalItems)) {
            loading = false
            previousTotalItems = totalItemCount
        }

        if (!loading && (lastVisiblePosition + VISIBLE_THRESHOLD) > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, recyclerView)
            loading = true
        }
    }

    fun resetState() {
        this.currentPage = STARTING_PAGE_INDEX
        this.previousTotalItems = 0
        this.loading = true
    }

    abstract fun onLoadMore(page: Int, recyclerView: RecyclerView)

    companion object {
        private const val VISIBLE_THRESHOLD = 5
        private const val STARTING_PAGE_INDEX = 0
    }

}