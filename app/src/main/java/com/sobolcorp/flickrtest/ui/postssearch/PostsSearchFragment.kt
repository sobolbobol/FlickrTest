package com.sobolcorp.flickrtest.ui.postssearch

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sobolcorp.flickrtest.R
import com.sobolcorp.flickrtest.base.DATA_ARGUMENT
import com.sobolcorp.flickrtest.databinding.PostsSearchFragmentBinding
import com.sobolcorp.flickrtest.network.models.Photo
import com.sobolcorp.flickrtest.ui.post.PostFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import java.lang.ref.WeakReference
import android.os.Build
import android.support.transition.TransitionInflater
import com.sobolcorp.flickrtest.base.TRANSITION_ARGUMENT


interface IAnimationPhotoNavigator {

    companion object {
        var currentAnimationPhotoNavigator: WeakReference<IAnimationPhotoNavigator>? = null
    }

    fun onNavigate(sharedView: View, photo: Photo)
}

class PostsSearchFragment : Fragment(), KodeinAware, IAnimationPhotoNavigator{

    override val kodein by closestKodein()

    companion object {
        fun newInstance() = PostsSearchFragment()
    }

    private var viewModel: PostsSearchViewModel? = null
    private var viewBinding: PostsSearchFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (viewModel == null || viewBinding == null) {
            viewModel = PostsSearchViewModel(kodein)
            viewModel!!.onCreateView(savedInstanceState, arguments)
            viewBinding = DataBindingUtil.inflate(inflater, R.layout.posts_search_fragment, container, false)
            viewBinding!!.viewModel = viewModel

        }

        return viewBinding!!.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel?.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    @SuppressLint("CheckResult")
    override fun onStart() {
        super.onStart()
        IAnimationPhotoNavigator.currentAnimationPhotoNavigator = WeakReference(this)
    }

    override fun onStop() {
        IAnimationPhotoNavigator.currentAnimationPhotoNavigator = null
        super.onStop()
    }

    override fun onNavigate(sharedView: View, photo: Photo) {
        val newFragment = PostFragment.newInstance().also { it.arguments = Bundle().also { it.putParcelable(DATA_ARGUMENT, photo) } }
        val fragmentTransaction = fragmentManager?.beginTransaction()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.photo_transition)
            exitTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.no_transition)

            newFragment.sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.photo_transition)
            newFragment.enterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.no_transition)

            sharedView.transitionName = "transition-${photo.id}"
            newFragment.arguments?.putString(TRANSITION_ARGUMENT, sharedView.transitionName)
            fragmentTransaction?.addSharedElement(sharedView, "transition-${photo.id}")
        }

        fragmentTransaction?.replace(R.id.container, newFragment, tag)
        fragmentTransaction?.addToBackStack(tag)
        fragmentTransaction?.commit()

    }
}