package com.sobolcorp.flickrtest.ui.postssearch.postitem

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sobolcorp.flickrtest.BR
import com.sobolcorp.flickrtest.R
import com.sobolcorp.flickrtest.databinding.PostItemBinding
import com.sobolcorp.flickrtest.network.models.Photo
import com.sobolcorp.flickrtest.recycler.BaseWrapper
import com.sobolcorp.flickrtest.recycler.ITEM_POST
import com.sobolcorp.flickrtest.recycler.ViewType

class PostItemWrapper : BaseWrapper() {

    override fun getViewType(): Int {
        return ITEM_POST
    }

    var position: Int? = null

    override fun getViewHolder(parent: ViewGroup?, inflater: LayoutInflater?): RecyclerView.ViewHolder {
        val binding: PostItemBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater!!, R.layout.post_item, parent, false) as PostItemBinding
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Photo) = with(itemView) {
            binding.setVariable(BR.viewModel, PostItemViewModel(item))
        }
    }

    override fun bind(holder: RecyclerView.ViewHolder?, item: ViewType) {
        if (item is Photo) {
            (holder as ViewHolder).bind(item)
        }
    }

    override fun checkItemType(item: Int): Boolean {
        if(item == ITEM_POST) {
            position = item
            return true
        }
        return false
    }

    override fun checkType(item: ViewType): Boolean {
        return item is Photo
    }
}