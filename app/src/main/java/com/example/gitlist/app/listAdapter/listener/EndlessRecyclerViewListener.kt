package com.example.gitlist.app.listAdapter.listener

import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerViewListener : RecyclerView.OnScrollListener() {

    private var currentPage = 0

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

}