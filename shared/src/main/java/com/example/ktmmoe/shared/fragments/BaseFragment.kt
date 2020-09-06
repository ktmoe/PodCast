package com.example.ktmmoe.shared.fragments

import android.app.Dialog
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.ktmmoe.shared.R
import com.example.ktmmoe.shared.mvp.views.BaseView

/**
 * Created by ktmmoe on 29, August, 2020
 **/
abstract class BaseFragment: Fragment(), BaseView {

    private var loadingView: Dialog? = null

    abstract fun setupPresenter()

    override fun showLoading() {
        if (loadingView == null) {
            loadingView = Dialog(requireContext())
        }
        loadingView?.let {
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.layout_loading_dialog, null)
            it.setContentView(view)
            it.setCancelable(false)
            it.show()
            it.window?.setBackgroundDrawable(null)
        }
    }

    override fun hideLoading() {
        loadingView?.let {
            if (it.isShowing) {
                it.cancel()
                loadingView = null
            }
        }
    }

    override fun getOwner(): LifecycleOwner = requireActivity()
}