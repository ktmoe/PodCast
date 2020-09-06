package com.example.ktmmoe.podcast.mvp.views

import com.example.ktmmoe.podcast.delegates.PodCastRecyclerDelegate
import com.example.ktmmoe.shared.mvp.views.BaseView

/**
 * Created by ktmmoe on 30, August, 2020
 **/
interface DownloadView: BaseView, PodCastRecyclerDelegate {
    fun setupRecycler()
    fun displayPodCastList()
}