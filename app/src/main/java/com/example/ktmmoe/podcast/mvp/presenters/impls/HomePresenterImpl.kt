package com.example.ktmmoe.podcast.mvp.presenters.impls

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.ktmmoe.podcast.data.models.FireStorePodCastModelImpl
import com.example.ktmmoe.podcast.data.models.PodCastModel
import com.example.ktmmoe.podcast.data.models.PodCastModelImpl
import com.example.ktmmoe.podcast.data.models.RealtimeDBPodCastModelImpl
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.mvp.presenters.HomePresenter
import com.example.ktmmoe.podcast.mvp.views.HomeView
import com.example.ktmmoe.podcast.network.responses.PodCastResponse
import com.example.ktmmoe.shared.mvp.presenters.AbstractBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.item_pod_cast.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.net.URL

/**
 * Created by ktmmoe on 30, August, 2020
 **/
class HomePresenterImpl: HomePresenter, AbstractBasePresenter<HomeView>() {
//    private val mPodCastModel : PodCastModel = PodCastModelImpl
//    private val mPodCastModel : PodCastModel = RealtimeDBPodCastModelImpl
    private val mPodCastModel : PodCastModel = FireStorePodCastModelImpl

    private val randomPodCast: MutableLiveData<PodCastResponse> = MutableLiveData()

    override fun onCreate() {
        mView?.setupViewPods()
        mView?.setupRecycler()
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        getData(lifecycleOwner)
        randomPodCast.observe(lifecycleOwner, Observer {
            mView?.displayRandomPodCast(it)
        })
    }

    @SuppressLint("CheckResult")
    override fun onDownload(
        podCast: PodCastWrapper,
        fragmentActivity: FragmentActivity,
        itemView: View
    ) {
        mPodCastModel.saveDownload(podCast){
            mView?.showErrorSnackBar(it)
        }

        itemView.tvPodCastDescription.visibility = View.GONE
        itemView.llDownloadProgress.visibility = View.VISIBLE

        val downloadManager = fragmentActivity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(podCast.data.audio)
        val request = DownloadManager.Request(uri)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "${podCast.id}.mp3")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        downloadManager.enqueue(request)
    }

    private fun getData(lifecycleOwner: LifecycleOwner) {
        mPodCastModel.getRandomPodCast {
            mView?.showErrorSnackBar(it)
        }.observe(lifecycleOwner, Observer {
            val a = it
            if (it.isNotEmpty())
                randomPodCast.postValue(it.first())
        })

        mPodCastModel.getPlayList {
            mView?.showErrorSnackBar(it)
        }.observe(lifecycleOwner, Observer {
            val a = it
            mView?.displayPodCastList(it)
        })
    }
}