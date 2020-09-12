package com.example.ktmmoe.podcast.utils

import android.content.Context
import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.ktmmoe.podcast.R
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

/**
 * Created by ktmmoe on 12, September, 2020
 **/
fun AppCompatImageView.load(source: Int, placeholder: Int = R.mipmap.placeholder) {
    Glide.with(this)
        .load(source)
        .into(this)
}

fun AppCompatImageView.load(source: String, placeholder: Int = R.mipmap.placeholder) {
    Glide.with(this)
        .load(source)
        .into(this)
}

fun buildMediaSource(context: Context, uri: Uri): MediaSource {
    val dataSourceFactory: DataSource.Factory =
        DefaultDataSourceFactory(context, "ExoPlayer")
    return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
}