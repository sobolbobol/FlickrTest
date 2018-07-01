package com.sobolcorp.flickrtest.ui.postssearch.postitem

import android.databinding.Bindable
import android.view.View
import com.sobolcorp.flickrtest.BR
import com.sobolcorp.flickrtest.base.BaseViewModel
import com.sobolcorp.flickrtest.glide.getImagePathFromPhoto
import com.sobolcorp.flickrtest.network.models.Photo
import com.sobolcorp.flickrtest.ui.postssearch.IAnimationPhotoNavigator


class PostItemViewModel(val data: Photo): BaseViewModel() {

    @Bindable
    var imageUrl = ""
    set(value) {
        field = value
        notifyPropertyChanged(BR.imageUrl)
    }

    init {
        imageUrl = getImagePathFromPhoto(data)
    }

    fun onItemClick(view: View) {
        IAnimationPhotoNavigator.currentAnimationPhotoNavigator?.get()?.onNavigate(view, data)
    }

}