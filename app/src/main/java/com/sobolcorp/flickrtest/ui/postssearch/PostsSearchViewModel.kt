package com.sobolcorp.flickrtest.ui.postssearch

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.Bindable
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.sobolcorp.flickrtest.BR
import com.sobolcorp.flickrtest.R
import com.sobolcorp.flickrtest.app.ProjectApp
import com.sobolcorp.flickrtest.base.BaseViewModel
import com.sobolcorp.flickrtest.network.api.RequestApi
import com.sobolcorp.flickrtest.recycler.*
import com.sobolcorp.flickrtest.ui.postssearch.postitem.PostItemWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import java.util.*

class PostsSearchViewModel(kodein: Kodein) : BaseViewModel() {

    var adapter: BaseRecyclerAdapter? = null
    val api: RequestApi by kodein.instance()
    val context: Context by kodein.instance()

    var request: Disposable? = null

    var searchText: String? = null

    @Bindable
    var searchTextWatcher: TextWatcher? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.searchTextWatcher)
        }

    @Bindable
    var legacyRequests = mutableListOf<String>()
        set(value) {
            field = value
            notifyPropertyChanged(BR.legacyRequests)
        }

    @Bindable
    var layoutManager: String = MANAGER_GRID_BY_TWO
        set(value) {
            field = value
            notifyPropertyChanged(BR.layoutManager)
        }

    init {
        initAdapter()
        initSearch()
    }

    fun initSearch() {

        var timer = Timer()
        val DELAY = 600L

        searchTextWatcher = object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                adapter?.isMoreItems = false
                searchText = p0.toString()

                Handler(Looper.getMainLooper()).post {
                    if(request?.isDisposed == false) {
                        request?.dispose()
                        request = null
                    }
                    adapter?.clearList()

//                    if(adapter?.items.last() !is ListLoadingData){
//                        adapter.addLoading()
//                    }
                }

                timer.cancel()
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        Handler(Looper.getMainLooper()).post {
//                            adapter?.removeItemsSublist(1, adapter.items.size)
                            adapter?.page = 1
                            adapter?.isLoading = false
                            adapter?.isMoreItems = true
                            adapter?.loadItems()
                        }
                    }
                }, DELAY)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                timer.cancel()
            }

        }
    }

    fun initAdapter() {
        val wrappers = mutableListOf<BaseWrapper>()
        wrappers.add(PostItemWrapper())
        adapter = BaseRecyclerAdapter(ViewTypeDelegateAdapter(object : ILoadPage {

            override fun loadPage(page: Int, limit: Int) {
                if(searchText.isNullOrEmpty()) {
                    getPhotos(page, limit)
                } else {
                    searchPhotos(page, limit)
                }
            }
        }, wrappers))
        adapter?.loadItems()
    }

    @SuppressLint("CheckResult")
    fun searchPhotos(page: Int, limit: Int) {
        if(page == 1) {
            addSearchToLegacy()
        }

        request = api.searchPhotos(text = searchText, page = page, perPage = limit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .retry(2)
                .subscribe({
                    it ->
                    if (it.stat == "ok"){
                        adapter?.addPage(it.photos.photo)
                    } else {
                        adapter?.isMoreItems = false
                    }
                }, { error ->
                    adapter?.isMoreItems = false
                    Toast.makeText(context.applicationContext, context.getString(R.string.error), Toast.LENGTH_LONG).show()
                })
    }

    fun addSearchToLegacy() {
        if(!searchText.isNullOrEmpty() && !legacyRequests.contains(searchText)) {
            legacyRequests.add(searchText!!)
            notifyPropertyChanged(BR.legacyRequests)
        }
    }

    @SuppressLint("CheckResult")
    fun getPhotos(page: Int, limit: Int) {
        request = api.getPhotos(page = page, perPage = limit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .retry(2)
                .subscribe({
                    it ->
                    if (it.stat == "ok"){
                        adapter?.addPage(it.photos.photo)
                    } else {
                        adapter?.isMoreItems = false
                    }
                }, { error ->
                    adapter?.isMoreItems = false
                    Toast.makeText(context.applicationContext, context.getString(R.string.error), Toast.LENGTH_LONG).show()
                })
    }

}