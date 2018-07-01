package com.sobolcorp.flickrtest.network

import com.sobolcorp.flickrtest.network.api.API_BASE_URL
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val FLICKR_KEY = "27f41ffe1dc9298482ba3928e3acc33f"
const val FLICKR_SECRET = "107f5d529c5d9a0b"

class NetworkModule {

    fun provideRetrofit(): Retrofit {
        val builder = OkHttpClient.Builder()
        builder.initTimeout()
        builder.setRetry()
        builder.initX509()

        builder.addInterceptor { chain ->

            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", FLICKR_KEY)
                    .addQueryParameter("format", "json")
                    .addQueryParameter("nojsoncallback", "1")
                    .build()

            val requestBuilder = original.newBuilder()
                    .url(url)

            val request = requestBuilder?.build()

            chain.proceed(request)
        }

        NetworkInterceptors().addInterceptors(builder)

        return Retrofit.Builder().baseUrl(API_BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}

val networkModule = Kodein.Module("Network Module") {
    bind<NetworkModule>() with singleton { NetworkModule() }
}