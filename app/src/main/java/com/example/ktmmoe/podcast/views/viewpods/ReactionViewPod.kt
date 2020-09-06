package com.example.ktmmoe.podcast.views.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_pod_reaction.view.*

/**
 * Created by ktmmoe on 02, September, 2020
 **/
class ReactionViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    fun setData(duration_: String, fire_: Int, comment_: Int){
        duration.text = duration_
        fire.text = fire_.toString()
        comment.text = comment_.toString()
    }
}