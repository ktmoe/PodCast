package com.example.ktmmoe.podcast.views.viewpods

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.AttributeSet
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.utils.ExoPlayerEventListener
import com.example.ktmmoe.podcast.utils.buildMediaSource
import com.example.ktmmoe.podcast.utils.buildOfflineMediaSource
import com.example.ktmmoe.podcast.utils.load
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerControlView
import kotlinx.android.synthetic.main.activity_pod_cast_detail.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.large_media_playback.view.*

/**
 * Created by ktmmoe on 12, September, 2020
 **/
class ExoPlayerViewPod @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : PlayerControlView(context, attrs, defStyleAttr) {

    private var playWhenReady = false
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private val exoPlayerEventListener = ExoPlayerEventListener()
    private var simpleExoplayer: SimpleExoPlayer? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        initializePlayer()
    }

    private fun initializePlayer() {
        simpleExoplayer = SimpleExoPlayer.Builder(context).build()
        if (home_music_player != null) {
            home_music_player.player = simpleExoplayer
        } else playBackCard.player = simpleExoplayer
    }

    fun releasePlayer() {
        if (simpleExoplayer != null) {
            playWhenReady = simpleExoplayer!!.playWhenReady
            playbackPosition = simpleExoplayer!!.currentPosition
            currentWindow = simpleExoplayer!!.currentWindowIndex
            simpleExoplayer?.removeListener(exoPlayerEventListener)
            simpleExoplayer?.release()
            simpleExoplayer = null
        }
    }

    fun setData(wrapper: PodCastWrapper, isDownloaded: Boolean) {
        val uri = if (isDownloaded) Uri.parse("${Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS)}/${wrapper.id}.mp3") else Uri.parse(wrapper.data.audio)
        val mediaSource = if (isDownloaded) buildOfflineMediaSource(context, uri) else buildMediaSource(context, uri)
        simpleExoplayer?.playWhenReady = playWhenReady
        simpleExoplayer?.seekTo(currentWindow, playbackPosition)
        simpleExoplayer?.addListener(exoPlayerEventListener)
        simpleExoplayer?.prepare(mediaSource, false, false)
    }
}