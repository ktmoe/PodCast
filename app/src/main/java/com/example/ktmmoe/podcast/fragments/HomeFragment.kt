package com.example.ktmmoe.podcast.fragments

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ktmmoe.podcast.R
import com.example.ktmmoe.podcast.activities.PodCastDetailActivity
import com.example.ktmmoe.podcast.adapters.recycleradapters.PodCastRecyclerAdapter
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.mvp.presenters.HomePresenter
import com.example.ktmmoe.podcast.mvp.presenters.impls.HomePresenterImpl
import com.example.ktmmoe.podcast.mvp.views.HomeView
import com.example.ktmmoe.podcast.network.responses.PodCastResponse
import com.example.ktmmoe.podcast.utils.ExoPlayerWorks
import com.example.ktmmoe.podcast.utils.load
import com.example.ktmmoe.podcast.views.viewpods.ExoPlayerViewPod
import com.example.ktmmoe.shared.fragments.BaseFragment
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.large_media_playback.*
import kotlinx.android.synthetic.main.large_media_playback.view.*
import kotlinx.android.synthetic.main.layout_exo_controller.*

class HomeFragment : BaseFragment(), HomeView, Player.EventListener  {

    private lateinit var mPresenter: HomePresenterImpl
    private lateinit var podCastRecyclerAdapter: PodCastRecyclerAdapter

    private lateinit var mExoplayerViewPod: ExoPlayerViewPod

    private lateinit var dm: DownloadManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        setupPresenter()
        return view
    }

    override fun setupPresenter() {
        mPresenter = ViewModelProviders.of(this).get(HomePresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.onCreate()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onUiReady(this)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun setupRecycler() {
        recyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        podCastRecyclerAdapter = PodCastRecyclerAdapter(this)
        recyclerview.adapter = podCastRecyclerAdapter
    }

    override fun setupViewPods() {
        mExoplayerViewPod = home_music_player as ExoPlayerViewPod
    }

    override fun displayPodCastList(podCastList: List<PodCastWrapper>) {
        podCastRecyclerAdapter.setNewData(podCastList.toMutableList())
    }

    override fun displayRandomPodCast(podCast: PodCastResponse) {
        tv_song_title.text = podCast.title
        imv_song_cover.load(podCast.image)
        mExoplayerViewPod.setData(PodCastWrapper(data = podCast), isDownloaded = false)
    }

    override fun showErrorSnackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onTapPodCastItem(podCastWrapper: PodCastWrapper) {
        startActivity(PodCastDetailActivity.newIntent(requireContext(), podCastWrapper, false))
    }

    override fun onTapDownload(podCast: PodCastWrapper, itemView: View) {
        mPresenter.onDownload(podCast, requireActivity(), itemView)
    }


    override fun onStop() {
        super.onStop()
        mExoplayerViewPod.releasePlayer()
    }

}