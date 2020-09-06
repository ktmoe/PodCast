package com.example.ktmmoe.podcast.adapters.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.ktmmoe.podcast.R
import com.example.ktmmoe.podcast.data.vos.Genre
import com.example.ktmmoe.podcast.delegates.PodCastRecyclerDelegate
import com.example.ktmmoe.podcast.views.viewholders.CategoryListViewHolder
import com.example.ktmmoe.podcast.views.viewholders.PodCastListViewHolder
import com.example.ktmmoe.shared.adapters.BaseRecyclerAdapter

/**
 * Created by ktmmoe on 30, August, 2020
 **/
class CategoryRecyclerAdapter(): BaseRecyclerAdapter<CategoryListViewHolder, Genre>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        return CategoryListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false))
    }
}