package com.sobolcorp.flickrtest.network

import okhttp3.OkHttpClient

interface BaseInterceptors {

    fun addInterceptors(builder: OkHttpClient.Builder)

}