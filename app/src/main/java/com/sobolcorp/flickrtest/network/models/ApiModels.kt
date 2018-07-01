package com.sobolcorp.flickrtest.network.models

import android.os.Parcelable
import com.sobolcorp.flickrtest.recycler.ViewType
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(val id: String?,
                 val owner: String?,
                 val secret: String?,
                 val server: String?,
                 val farm: Int?,
                 val title: String?,
                 val ispublic: Int?,
                 val isfriend: Int?,
                 val isfamily: Int?): ViewType, Parcelable

data class Photos(val page: Int?,
                  val pages: Int?,
                  val perpage: Int?,
                  val total: Int?,
                  val photo: List<Photo>)