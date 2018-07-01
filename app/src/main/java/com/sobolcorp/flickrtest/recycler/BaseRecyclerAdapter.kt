package com.sobolcorp.flickrtest.recycler

import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class BaseRecyclerAdapter(private val delegate: ViewTypeDelegateAdapter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegate.onBindViewHolder(holder, items[position])

        if (items.size - position < limit / 2 && !isLoading && isMoreItems) {
            loadItems()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegate.onCreateViewHolder(parent, viewType)
    }

    var page = 1
    var limit = 20
    var isLoading = false
    var isMoreItems = true

    var items: MutableList<ViewType> = mutableListOf()


    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {

        val itemType: Int? = delegate.getItemViewType(items[position])
        if (itemType != null) return itemType
        return 0
    }

    fun addPage(newItems: List<ViewType>) {
        page++
        addList(newItems)
    }

    fun addList(newItems: List<ViewType>?) {
        if (newItems == null || newItems.isEmpty()) {
            if (items.size == 0) {
                isMoreItems = false
            }
            return
        }
        val initPosition = if (items.lastIndex < 0) 0 else items.lastIndex + 1
        items.addAll(newItems)

        if(newItems.size < limit) {
            isMoreItems = false
        }

        notifyItemRangeChanged(initPosition, items.size)
        isLoading = false
    }

    fun loadItems() {
        Handler().post {
            if (!isLoading) {
                isLoading = true
                if (items.size == 0) {
                }
                delegate.loadItems(page, limit)
            }
        }
    }

    fun clearList() {
        items.clear()
        page = 1
        notifyDataSetChanged()
    }

}