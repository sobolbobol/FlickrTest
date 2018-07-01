package com.sobolcorp.flickrtest.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sobolcorp.flickrtest.ui.postssearch.postitem.PostItemWrapper

class ViewTypeDelegateAdapter(private val loader: ILoadPage?, private val wrappers: List<BaseWrapper>) {


    var inflater: LayoutInflater? = null

     fun getItemViewType(itemType: ViewType): Int? {
         wrappers.forEach {
             if (it.checkType(itemType)) {
                return it.getViewType()
             }
         }
        return null
    }

     fun onCreateViewHolder(parent: ViewGroup, itemType: Int): RecyclerView.ViewHolder {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }

        wrappers.forEach {
            if(it.checkItemType(itemType)) {
                return it.getViewHolder(parent, inflater)
            }
        }
        return PostItemWrapper().getViewHolder(parent, inflater)
    }

     fun onBindViewHolder(holder: RecyclerView.ViewHolder?, item: ViewType) {
        wrappers.forEach {
            if(it.checkType(item)) {
                it.bind(holder, item)
            }
        }
    }

    fun loadItems(page: Int, limit: Int) {
        loader?.loadPage(page, limit)
    }

}