package com.sobolcorp.flickrtest.ui.post

import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sobolcorp.flickrtest.R
import com.sobolcorp.flickrtest.databinding.PostFragmentBinding

//Kotlin can do databinding & this, what for Butterknife?
import kotlinx.android.synthetic.main.post_fragment.*

class PostFragment: Fragment() {

    companion object {
        fun newInstance() = PostFragment()
    }

    private var viewModel: PostViewModel? = null
    private var viewBinding: PostFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (viewModel == null || viewBinding == null) {
            viewModel = PostViewModel()
            viewModel!!.onCreateView(savedInstanceState, arguments)
            viewBinding = DataBindingUtil.inflate(inflater, R.layout.post_fragment, container, false)
            viewBinding!!.viewModel = viewModel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                viewBinding!!.photo.transitionName = "image"
            }

        }

        return viewBinding!!.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }
}