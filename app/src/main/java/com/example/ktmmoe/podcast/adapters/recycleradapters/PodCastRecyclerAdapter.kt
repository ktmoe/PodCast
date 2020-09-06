package com.example.ktmmoe.podcast.adapters.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ktmmoe.podcast.R
import com.example.ktmmoe.podcast.data.vos.PodCast
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.delegates.PodCastRecyclerDelegate
import com.example.ktmmoe.podcast.views.viewholders.PodCastListViewHolder
import com.example.ktmmoe.shared.adapters.BaseRecyclerAdapter

/**
 * Created by ktmmoe on 30, August, 2020
 **/
class PodCastRecyclerAdapter(private val delegate: PodCastRecyclerDelegate, private val isDownloaded: Boolean=false): BaseRecyclerAdapter<PodCastListViewHolder, PodCastWrapper>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodCastListViewHolder {
        return PodCastListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pod_cast, parent, false), delegate, isDownloaded)
    }
}