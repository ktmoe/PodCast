package com.example.ktmmoe.podcast.mvp.presenters.impls

import com.example.ktmmoe.podcast.mvp.presenters.MainPresenter
import com.example.ktmmoe.podcast.mvp.views.MainView
import com.example.ktmmoe.shared.mvp.presenters.AbstractBasePresenter

/**
 * Created by ktmmoe on 30, August, 2020
 **/
class MainPresenterImpl: MainPresenter, AbstractBasePresenter<MainView>() {
    override fun onCreate() {
        mView?.setupViewPager()
        mView?.setupBottomNavigation()
    }
}