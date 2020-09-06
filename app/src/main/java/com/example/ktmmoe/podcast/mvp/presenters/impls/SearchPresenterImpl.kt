package com.example.ktmmoe.podcast.mvp.presenters.impls

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.ktmmoe.podcast.data.models.PodCastModel
import com.example.ktmmoe.podcast.data.models.PodCastModelImpl
import com.example.ktmmoe.podcast.mvp.presenters.DownloadPresenter
import com.example.ktmmoe.podcast.mvp.presenters.SearchPresenter
import com.example.ktmmoe.podcast.mvp.views.DownloadView
import com.example.ktmmoe.podcast.mvp.views.SearchView
import com.example.ktmmoe.shared.mvp.presenters.AbstractBasePresenter

/**
 * Created by ktmmoe on 01, September, 2020
 **/
class SearchPresenterImpl: SearchPresenter, AbstractBasePresenter<SearchView>() {
    private val mPodCastModel: PodCastModel = PodCastModelImpl

    override fun onCreate() {
        mView?.setupRecycler()
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        getPodCastGenres(lifecycleOwner)
    }

    private fun getPodCastGenres(lifecycleOwner: LifecycleOwner) {
        mPodCastModel.getGenres {
            Log.d("ErrorTag", it)
        }.observe(lifecycleOwner, Observer {
            mView?.displayCategoryList(it)
        })
    }
}