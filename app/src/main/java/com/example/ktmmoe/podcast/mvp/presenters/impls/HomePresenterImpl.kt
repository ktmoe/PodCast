package com.example.ktmmoe.podcast.mvp.presenters.impls

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ProgressBar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.ktmmoe.podcast.data.models.PodCastModel
import com.example.ktmmoe.podcast.data.models.PodCastModelImpl
import com.example.ktmmoe.podcast.mvp.presenters.HomePresenter
import com.example.ktmmoe.podcast.mvp.views.HomeView
import com.example.ktmmoe.podcast.network.responses.PodCastResponse
import com.example.ktmmoe.shared.mvp.presenters.AbstractBasePresenter
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
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
    private val mPodCastModel : PodCastModel = PodCastModelImpl

    private val randomPodCast: MutableLiveData<PodCastResponse> = MutableLiveData()

    private val progress: MutableLiveData<Int> = MutableLiveData()
    private var progressBar: ProgressBar? = null

    override fun onCreate() {
        mView?.setupRecycler()
    }

    override fun onUiReady(lifecycleOwner: LifecycleOwner) {
        getData(lifecycleOwner)
        randomPodCast.observe(lifecycleOwner, Observer {
            mView?.displayRandomPodCast(it)
        })
        progress.observe(lifecycleOwner, Observer {p->
            progressBar?.let {
                mView?.updateProgress(p, it)
            }
        })
    }

    @SuppressLint("CheckResult")
    override fun onDownload(
        url: String,
        fragmentActivity: FragmentActivity,
        progressBar: ProgressBar
    ) {
        this.progressBar = progressBar

        val downloadManager = fragmentActivity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        val id = downloadManager.enqueue(request)

        var downloading = true

        Observable.just(downloading)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe{
                val query = DownloadManager.Query()
                query.setFilterById(id)
                val cursor = downloadManager.query(query)
                cursor.moveToFirst()
                while (downloading) {
                    val bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    val totalBytes = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                    Log.d("DOWNLOAD", "$bytesDownloaded, $totalBytes")
                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false
                        cursor.close()
                    }
                    else progress.postValue((bytesDownloaded * 100) / totalBytes)
                }
            }
//        while (downloading) {
//            cursor.moveToFirst()
//            val bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
//            if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
//                downloading = false
//                cursor.close()
//            }
//            else progress.postValue(bytesDownloaded)

//        }
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