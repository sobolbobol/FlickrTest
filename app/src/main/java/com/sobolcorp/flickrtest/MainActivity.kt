package com.sobolcorp.flickrtest

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sobolcorp.flickrtest.base.BaseActivityCatcher
import com.sobolcorp.flickrtest.base.RxBus
import com.sobolcorp.flickrtest.ui.postssearch.PostsSearchFragment
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, PostsSearchFragment.newInstance()).commit()
        }
        RxBus.listen(String::class.java).subscribe({
            onBackPressed()
        })
    }

    override fun onResume() {
        super.onResume()
        BaseActivityCatcher.baseActivity = WeakReference(this)
    }
}
