package com.example.ktmmoe.podcast.delegates

import android.widget.ProgressBar
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper

/**
 * Created by ktmmoe on 30, August, 2020
 **/
interface PodCastRecyclerDelegate {
    fun onTapPodCastItem(podCastWrapper: PodCastWrapper)

    fun onTapDownload(url: String, progressBar: ProgressBar)
}