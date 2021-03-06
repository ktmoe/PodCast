package com.example.ktmmoe.podcast.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.example.ktmmoe.podcast.mvp.views.DownloadView
import com.example.ktmmoe.podcast.mvp.views.SearchView
import com.example.ktmmoe.shared.mvp.presenters.BasePresenter

/**
 * Created by ktmmoe on 30, August, 2020
 **/
interface SearchPresenter: BasePresenter<SearchView> {
    fun onCreate()
    fun onUiReady(lifecycleOwner: LifecycleOwner)
}