package com.sobolcorp.flickrtest.glide

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageView
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.sobolcorp.flickrtest.network.models.Photo

@BindingAdapter("bind:imageUrl", "bind:imagePh", requireAll = false)
fun setImage(view: AppCompatImageView, image: String?, placeholder: Drawable?) {

    if (image.isNullOrEmpty()) return
    var options = RequestOptions()
    if (placeholder != null) {
        options = options.placeholder(placeholder)
    }

    GlideApp.with(view.context)
            .load(image)
            .apply(options)
            .into(view)
}

@BindingAdapter("bind:imageBigUrl", "bind:imageBigPh", requireAll = false)
fun setImageBig(view: AppCompatImageView, image: String?, placeholder: Drawable?) {

    if (image.isNullOrEmpty()) return
    var options = RequestOptions()
    if (placeholder != null) {
        options = options.placeholder(placeholder)
    }

    GlideApp.with(view.context)
            .asBitmap()
            .load(image)
            .apply(options)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    view.setImageBitmap(resource)
                }


            })
}

fun getImagePathFromPhoto(photo: Photo, isSmall: Boolean = true): String {
    val path = "http://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_${if(isSmall) "n" else "b"}.jpg"
    return path
}