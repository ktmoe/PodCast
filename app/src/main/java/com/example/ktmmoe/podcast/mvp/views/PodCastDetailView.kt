package com.example.ktmmoe.podcast.mvp.views

import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.shared.mvp.views.BaseView

/**
 * Created by ktmmoe on 30, August, 2020
 **/
interface PodCastDetailView: BaseView {
    fun setupViewPods()
    fun bindData(podCastWrapper: PodCastWrapper)
}