package com.example.ktmmoe.podcast.activities

import android.content.Context
import android.content.Intent
import android.media.session.PlaybackState
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.Html
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.ktmmoe.podcast.R
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.mvp.presenters.PodCastDetailPresenter
import com.example.ktmmoe.podcast.mvp.presenters.impls.PodCastDetailPresenterImpl
import com.example.ktmmoe.podcast.mvp.views.PodCastDetailView
import com.example.ktmmoe.shared.activities.BaseActivity
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_pod_cast_detail.placeholder
import kotlinx.android.synthetic.main.activity_pod_cast_detail.tvPodCastDescription
import kotlinx.android.synthetic.main.activity_pod_cast_detail.tvPodCastTitle
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.large_media_playback.*
import kotlinx.android.synthetic.main.large_media_playback.playerControlView
import kotlinx.android.synthetic.main.layout_exo_controller.*
import kotlinx.android.synthetic.main.playback_card.*

class PodCastDetailActivity : BaseActivity(), PodCastDetailView, Player.EventListener {
    private lateinit var mPresenter: PodCastDetailPresenter

    private lateinit var mExoPlayer: SimpleExoPlayer

    private var playbackPosition: Long = 0

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

    override fun bindData(podCastWrapper: PodCastWrapper) {
        val isDownloaded = intent.getBooleanExtra("d", false)

        Glide.with(this)
            .load(podCastWrapper.data.image)
            .into(placeholder)

        tvPodCastTitle.text = podCastWrapper.data.title
        tvPodCastDescription.text = Html.fromHtml(podCastWrapper.data.description)

        val userAgent = Util.getUserAgent(this, "exoPlayer")
        val uri = if (isDownloaded) Uri.parse("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/${podCastWrapper.id}.mp3") else Uri.parse(podCastWrapper.data.audio)

        val trackSelector = DefaultTrackSelector(this)
        trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd())

        mExoPlayer = SimpleExoPlayer.Builder(this)
            .setTrackSelector(trackSelector)
            .build()
//        mExoPlayer = ExoPlayerFactory.newSimpleInstance(this)

        if (!isDownloaded) {
            val httpDataSourceFactory = DefaultHttpDataSourceFactory(
                userAgent,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true
            )

            val dataSourceFactory = DefaultDataSourceFactory(this, null, httpDataSourceFactory)

            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri)

            mExoPlayer.prepare(mediaSource)
        } else {
            val mediaSource = ExtractorMediaSource(uri, DefaultDataSourceFactory(this, userAgent),
            DefaultExtractorsFactory(), null, null)
            mExoPlayer.prepare(mediaSource)
        }

        playerControlView.player = mExoPlayer
        mExoPlayer.seekTo(playbackPosition)

        mExoPlayer.addListener(this)
        ivPause.setOnClickListener {
            mExoPlayer.playWhenReady = !mExoPlayer.isPlaying

        }
        ivRewind.setOnClickListener {
            mExoPlayer.seekTo(mExoPlayer.currentPosition - 10000)
        }
        ivSkip.setOnClickListener {
            mExoPlayer.seekTo(mExoPlayer.currentPosition + 30000)
        }
    }

    private fun releasePlayer() {
        playbackPosition = mExoPlayer.currentPosition
        mExoPlayer.release()
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