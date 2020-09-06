package com.example.ktmmoe.podcast.views.viewholders

import android.view.View
import com.example.ktmmoe.podcast.data.vos.Genre
import com.example.ktmmoe.podcast.delegates.PodCastRecyclerDelegate
import com.example.ktmmoe.shared.views.viewholders.BaseViewHolder
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_pod_cast.view.*
import kotlinx.android.synthetic.main.item_pod_cast.view.placeholder

/**
 * Created by ktmmoe on 30, August, 2020
 **/
class CategoryListViewHolder(itemView: View): BaseViewHolder<Genre>(itemView) {

    override fun bindData(data: Genre) {
        mData = data
    }
}