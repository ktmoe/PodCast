package com.example.ktmmoe.podcast.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.mvp.views.PodCastDetailView
import com.example.ktmmoe.shared.mvp.presenters.BasePresenter

/**
 * Created by ktmmoe on 30, August, 2020
 **/
interface PodCastDetailPresenter: BasePresenter<PodCastDetailView> {
    fun onUiReady(lifecycleOwner: LifecycleOwner, podCastWrapper: PodCastWrapper)
}