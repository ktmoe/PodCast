package com.example.ktmmoe.podcast.mvp.views

import android.widget.ProgressBar
import com.example.ktmmoe.podcast.data.vos.PodCast
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.delegates.PodCastRecyclerDelegate
import com.example.ktmmoe.podcast.network.responses.PodCastResponse
import com.example.ktmmoe.shared.mvp.views.BaseView

/**
 * Created by ktmmoe on 30, August, 2020
 **/
interface HomeView: BaseView, PodCastRecyclerDelegate {
    fun setupRecycler()
    fun displayPodCastList(podCastList: List<PodCastWrapper>)
    fun displayRandomPodCast(podCast: PodCastResponse)
    fun showErrorSnackBar(message: String)
    fun updateProgress(progress: Int, progressBar: ProgressBar)
}