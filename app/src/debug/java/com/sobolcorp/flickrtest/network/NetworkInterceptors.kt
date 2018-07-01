package com.sobolcorp.flickrtest.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class NetworkInterceptors : BaseInterceptors {

    override fun addInterceptors(builder: OkHttpClient.Builder) {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
    }

}