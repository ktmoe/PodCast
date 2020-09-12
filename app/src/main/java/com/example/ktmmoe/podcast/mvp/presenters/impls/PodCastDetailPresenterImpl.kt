package com.example.ktmmoe.podcast.mvp.presenters.impls

import androidx.lifecycle.LifecycleOwner
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.mvp.presenters.PodCastDetailPresenter
import com.example.ktmmoe.podcast.mvp.views.PodCastDetailView
import com.example.ktmmoe.shared.mvp.presenters.AbstractBasePresenter

/**
 * Created by ktmmoe on 30, August, 2020
 **/
class PodCastDetailPresenterImpl: PodCastDetailPresenter, AbstractBasePresenter<PodCastDetailView>() {

    override fun onUiReady(lifecycleOwner: LifecycleOwner, podCastWrapper: PodCastWrapper) {
        mView?.setupViewPods()
        mView?.bindData(podCastWrapper)
    }
}