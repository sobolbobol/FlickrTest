package com.sobolcorp.flickrtest.network.api

import com.sobolcorp.flickrtest.network.models.PhotosResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val API_BASE_URL: String = "https://api.flickr.com/"

interface RequestApi {

    @GET("services/rest/")
    fun searchPhotos(@Query("method") method: String = "flickr.photos.search",
                   @Query("text") text: String?,
                   @Query("page") page: Int,
                   @Query("per_page") perPage: Int): Single<PhotosResponse>

    @GET("services/rest/")
    fun getPhotos(@Query("method") method: String = "flickr.photos.getRecent",
                   @Query("page") page: Int,
                   @Query("per_page") perPage: Int): Single<PhotosResponse>
}