package com.example.ktmmoe.shared.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ktmmoe on 29, August, 2020
 **/
abstract class BaseViewHolder<T>(itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    var mData : T? = null

    abstract fun bindData(data : T)
}