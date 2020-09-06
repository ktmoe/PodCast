package com.example.ktmmoe.podcast.mvp.views

import com.example.ktmmoe.shared.mvp.views.BaseView

/**
 * Created by ktmmoe on 30, August, 2020
 **/
interface MainView : BaseView{
    fun setupViewPager()
    fun setupBottomNavigation()
}