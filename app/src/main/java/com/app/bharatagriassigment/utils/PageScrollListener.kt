package com.app.bharatagriassigment.utils

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PageScrollListener(var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount: Int = layoutManager.childCount
        val totalItemCount: Int = layoutManager.itemCount
        val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= PAGE_SIZE) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}