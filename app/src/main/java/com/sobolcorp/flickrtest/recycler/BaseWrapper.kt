package com.sobolcorp.flickrtest.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseWrapper {

    abstract fun getViewHolder(parent: ViewGroup?, inflater: LayoutInflater?): RecyclerView.ViewHolder
    abstract fun bind(holder: RecyclerView.ViewHolder?, item: ViewType)
    abstract fun checkType(item: ViewType): Boolean
    abstract fun checkItemType(item: Int): Boolean
    abstract fun getViewType() : Int

}