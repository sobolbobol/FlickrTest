package com.sobolcorp.flickrtest.recycler

import android.databinding.BindingAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

const val MANAGER_GRID_BY_TWO = "grid_by_2"

@BindingAdapter("bind:layoutManager")
fun setRecyclerViewLayoutManager(view: RecyclerView, manager: String?) {
    when (manager) {
        MANAGER_GRID_BY_TWO -> {
            val layoutManager = GridLayoutManager(view.context, 2)
            view.layoutManager = layoutManager
            view.addItemDecoration(GridSpacingItemDecoration(2, 50, true))
        }
    }
}

@BindingAdapter("bind:adapter")
fun setRecyclerViewAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?) {
    if (adapter == null) return
    view.adapter = adapter
}