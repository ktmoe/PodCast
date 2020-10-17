package com.example.ktmmoe.podcast.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.ViewModelProviders
import com.example.ktmmoe.podcast.R
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.mvp.presenters.PodCastDetailPresenter
import com.example.ktmmoe.podcast.mvp.presenters.impls.PodCastDetailPresenterImpl
import com.example.ktmmoe.podcast.mvp.views.PodCastDetailView
import com.example.ktmmoe.podcast.utils.load
import com.example.ktmmoe.podcast.views.viewpods.ExoPlayerViewPod
import com.example.ktmmoe.shared.activities.BaseActivity
import com.google.android.exoplayer2.Player
import kotlinx.android.synthetic.main.activity_pod_cast_detail.*

class PodCastDetailActivity : BaseActivity(), PodCastDetailView, Player.EventListener {
    private lateinit var mPresenter: PodCastDetailPresenter

    private lateinit var mExoplayerViewPod : ExoPlayerViewPod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pod_cast_detail)
        setUpPresenter()
        mPresenter.onUiReady(this, intent.getSerializableExtra("e") as PodCastWrapper)
    }

    override fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(PodCastDetailPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun setupViewPods() {
        mExoplayerViewPod = playBackCard as ExoPlayerViewPod
    }

    override fun bindData(podCastWrapper: PodCastWrapper) {
        val isDownloaded = intent.getBooleanExtra("d", false)
        placeholder.load(podCastWrapper.data.image?: "")

        tvPodCastTitle.text = podCastWrapper.data.title
        tvPodCastDescription.text = Html.fromHtml(podCastWrapper.data.description)

        mExoplayerViewPod.setData(podCastWrapper, isDownloaded = isDownloaded)

//        val userAgent = Util.getUserAgent(this, "exoPlayer")
//        val uri = if (isDownloaded) Uri.parse("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/${podCastWrapper.id}.mp3") else Uri.parse(podCastWrapper.data.audio)
//
//        val trackSelector = DefaultTrackSelector(this)
//        trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd())
//
//        if (!isDownloaded) {
//            val httpDataSourceFactory = DefaultHttpDataSourceFactory(
//                userAgent,
//                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
//                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
//                true
//            )
//
//            val dataSourceFactory = DefaultDataSourceFactory(this, null, httpDataSourceFactory)
//
//            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(uri)
//
//            mExoPlayer.prepare(mediaSource)
//        } else {
//            val mediaSource = ExtractorMediaSource(uri, DefaultDataSourceFactory(this, userAgent),
//            DefaultExtractorsFactory(), null, null)
//            mExoPlayer.prepare(mediaSource)
//        }

    }

    private fun releasePlayer() {
        mExoplayerViewPod.releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    companion object {
        fun newIntent(context: Context, podCastWrapper: PodCastWrapper, isDownloaded: Boolean): Intent = Intent(context, PodCastDetailActivity::class.java)
            .putExtra("e", podCastWrapper)
            .putExtra("d", isDownloaded)
    }
}