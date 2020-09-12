package com.example.ktmmoe.podcast.utils

import android.util.Log
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player

/**
 * Created by ktmmoe on 12, September, 2020
 **/
class ExoPlayerEventListener : Player.EventListener {

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        when (playbackState) {
            ExoPlayer.STATE_IDLE -> ExoPlayer.STATE_IDLE
            ExoPlayer.STATE_BUFFERING -> ExoPlayer.STATE_BUFFERING
            ExoPlayer.STATE_READY -> ExoPlayer.STATE_READY
            ExoPlayer.STATE_ENDED -> ExoPlayer.STATE_ENDED
            else -> "Unknown"
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        if (isPlaying) Log.d("Playing", isPlaying.toString())
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        super.onPlayerError(error)
        error.message?.let { Log.d("isPlaying", it) }
    }
}