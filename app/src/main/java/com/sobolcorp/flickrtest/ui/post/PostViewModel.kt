package com.sobolcorp.flickrtest.ui.post

import android.databinding.Bindable
import android.os.Bundle
import android.os.Handler
import com.sobolcorp.flickrtest.BR
import com.sobolcorp.flickrtest.base.BaseViewModel
import com.sobolcorp.flickrtest.base.DATA_ARGUMENT
import com.sobolcorp.flickrtest.base.RxBus
import com.sobolcorp.flickrtest.base.TRANSITION_ARGUMENT
import com.sobolcorp.flickrtest.glide.getImagePathFromPhoto
import com.sobolcorp.flickrtest.network.models.Photo

class PostViewModel : BaseViewModel() {

    @Bindable
    var transitionName = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transitionName)
        }

    @Bindable
    var imageUrl = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUrl)
        }

    @Bindable
    var title = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    override fun onCreateView(savedData: Bundle?, arguments: Bundle?) {
        if (arguments?.containsKey(DATA_ARGUMENT) == true) {
            val photo = arguments.getParcelable<Photo>(DATA_ARGUMENT)
            imageUrl = getImagePathFromPhoto(photo!!)
            Handler().postDelayed({imageUrl = getImagePathFromPhoto(photo, false)}, 600)
            title = photo.title ?: ""
        }
        if (arguments?.containsKey(TRANSITION_ARGUMENT) == true) {
            transitionName = arguments.getString(TRANSITION_ARGUMENT)
        }
    }

    fun onBackClick() {
        RxBus.publish("Testing")
    }

}