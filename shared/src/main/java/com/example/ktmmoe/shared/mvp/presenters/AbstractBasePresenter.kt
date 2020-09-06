package com.example.ktmmoe.shared.mvp.presenters

import androidx.lifecycle.ViewModel
import com.example.ktmmoe.shared.mvp.views.BaseView
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by ktmmoe on 30, August, 2020
 **/
abstract class AbstractBasePresenter<T: BaseView> : BasePresenter<T>, ViewModel() {
    var mView: T? = null

    override fun initPresenter(view: T) {
        mView = view
    }
}