package com.example.ktmmoe.podcast.mvp.presenters

import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.mvp.views.HomeView
import com.example.ktmmoe.shared.mvp.presenters.BasePresenter

/**
 * Created by ktmmoe on 30, August, 2020
 **/
interface HomePresenter: BasePresenter<HomeView> {
    fun onCreate()
    fun onUiReady(lifecycleOwner: LifecycleOwner)
    fun onDownload(podCast: PodCastWrapper, fragmentActivity: FragmentActivity, itemView: View)
}