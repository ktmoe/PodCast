package com.example.ktmmoe.podcast.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.example.ktmmoe.podcast.mvp.presenters.DownloadPresenter
import com.example.ktmmoe.podcast.mvp.views.DownloadView
import com.example.ktmmoe.shared.mvp.presenters.AbstractBasePresenter

/**
 * Created by ktmmoe on 30, August, 2020
 **/
class DownloadPresenterImpl: DownloadPresenter, AbstractBasePresenter<DownloadView>() {
    override fun onCreate() {
        mView?.setupRecycler()
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        mView?.displayPodCastList()
    }
}