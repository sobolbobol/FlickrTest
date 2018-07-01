package com.sobolcorp.flickrtest.base

import android.support.v7.app.AppCompatActivity
import java.lang.ref.WeakReference

interface BaseActivityCatcher {

    companion object {
        var baseActivity: WeakReference<AppCompatActivity>? = null
    }

}