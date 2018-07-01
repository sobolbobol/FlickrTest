package com.sobolcorp.flickrtest.app

import android.app.Application
import android.content.Context
import com.sobolcorp.flickrtest.network.NetworkModule
import com.sobolcorp.flickrtest.network.api.RequestApi
import com.sobolcorp.flickrtest.network.networkModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ProjectApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        bind<Context>() with singleton { this@ProjectApp.applicationContext }
        import(networkModule)
        bind<RequestApi>() with singleton {
            val networkModule: NetworkModule = instance()
            val retrofit = networkModule.provideRetrofit()
            return@singleton retrofit.create(RequestApi::class.java)
        }
    }
}