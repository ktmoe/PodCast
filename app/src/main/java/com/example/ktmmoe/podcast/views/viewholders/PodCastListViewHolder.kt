package com.example.ktmmoe.podcast.views.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.delegates.PodCastRecyclerDelegate
import com.example.ktmmoe.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.item_pod_cast.view.*

/**
 * Created by ktmmoe on 30, August, 2020
 **/
class PodCastListViewHolder(itemView: View, delegate: PodCastRecyclerDelegate, private val isDownloaded: Boolean = false): BaseViewHolder<PodCastWrapper>(itemView) {

    init {
        itemView.setOnClickListener {
            delegate.onTapPodCastItem(mData?: PodCastWrapper())
        }

        itemView.ivDownload.setOnClickListener {
            delegate.onTapDownload(mData?.data?.audio?:"", itemView.progressBar)
        }
    }

    override fun bindData(data: PodCastWrapper) {
        mData = data
        itemView.ivDownload.visibility = if (!isDownloaded) View.VISIBLE else View.GONE
        itemView.llDownloadProgress.visibility = if (!isDownloaded) View.VISIBLE else View.GONE
        itemView.tvPodCastDescription.visibility = if (isDownloaded) View.VISIBLE else View.GONE

        itemView.tvPodCastTitle.text = mData?.data?.title
        itemView.tvPodCastDescription.text = mData?.data?.description
        itemView.type.text = mData?.type
        Glide.with(itemView.context)
            .load(data.data.image)
            .into(itemView.placeholder)
    }
}