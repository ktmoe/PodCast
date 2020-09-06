package com.example.ktmmoe.podcast.mvp.presenters

import com.example.ktmmoe.podcast.mvp.views.MainView
import com.example.ktmmoe.shared.mvp.presenters.BasePresenter

/**
 * Created by ktmmoe on 30, August, 2020
 **/
interface MainPresenter : BasePresenter<MainView> {
    fun onCreate()
}