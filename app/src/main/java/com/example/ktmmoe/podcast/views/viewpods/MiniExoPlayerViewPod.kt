package com.example.ktmmoe.podcast.views.viewpods

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import com.example.ktmmoe.podcast.utils.ExoPlayerEventListener
import com.example.ktmmoe.podcast.utils.buildMediaSource
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerControlView
import kotlinx.android.synthetic.main.activity_pod_cast_detail.view.*

/**
 * Created by ktmmoe on 12, September, 2020
 **/
class MiniExoPlayerViewPod @JvmOverloads constructor(
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
        playBackCard.player = simpleExoplayer
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

    fun setData(audioUrl: String) {
        val uri = Uri.parse(audioUrl)
        val mediaSource = buildMediaSource(context,uri )
        simpleExoplayer?.playWhenReady = playWhenReady
        simpleExoplayer?.seekTo(currentWindow, playbackPosition)
        simpleExoplayer?.addListener(exoPlayerEventListener)
        simpleExoplayer?.prepare(mediaSource, false, false)
    }
}