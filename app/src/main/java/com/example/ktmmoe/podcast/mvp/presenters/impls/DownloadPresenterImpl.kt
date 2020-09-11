package com.example.ktmmoe.podcast.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.ktmmoe.podcast.data.models.PodCastModel
import com.example.ktmmoe.podcast.data.models.PodCastModelImpl
import com.example.ktmmoe.podcast.mvp.presenters.DownloadPresenter
import com.example.ktmmoe.podcast.mvp.views.DownloadView
import com.example.ktmmoe.shared.mvp.presenters.AbstractBasePresenter

/**
 * Created by ktmmoe on 30, August, 2020
 **/
class DownloadPresenterImpl: DownloadPresenter, AbstractBasePresenter<DownloadView>() {
    private val mPodCastModel: PodCastModel = PodCastModelImpl

    override fun onCreate() {
        mView?.setupRecycler()
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        mPodCastModel.getDownloads {
            mView?.showErrorSnackBar(it)
        }.observe(lifecycleOwner, Observer {
            mView?.displayPodCastList(it.map {d-> d.podCast })
        })
    }
}