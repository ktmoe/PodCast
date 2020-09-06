package com.example.ktmmoe.podcast.fragments

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
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
import com.example.ktmmoe.shared.fragments.BaseFragment
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.large_media_playback.*

class HomeFragment : BaseFragment(), HomeView, Player.EventListener {

    private lateinit var mPresenter: HomePresenter
    private lateinit var podCastRecyclerAdapter: PodCastRecyclerAdapter
    private lateinit var mExoPlayer: SimpleExoPlayer
    private var playbackPosition: Long = 0

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

    override fun displayPodCastList(podCastList: List<PodCastWrapper>) {
        podCastRecyclerAdapter.setNewData(podCastList.toMutableList())
    }

    override fun displayRandomPodCast(podCast: PodCastResponse) {
        tvPodCastTitle.text = podCast.title
        tvPodCastDescription.text = Html.fromHtml(podCast.description)
        Glide.with(requireContext())
            .load(podCast.image)
            .into(placeholder)

        setupExoPlayer(podCast)

    }

    private fun setupExoPlayer(podCast: PodCastResponse) {
        val userAgent = Util.getUserAgent(requireContext(), "exoPlayer")
        val httpDataSourceFactory = DefaultHttpDataSourceFactory(
            userAgent,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true
        )
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext())
        val dataSourceFactory = DefaultDataSourceFactory(context, null, httpDataSourceFactory)
        val uri = Uri.parse(podCast.audio)
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(uri)

        mExoPlayer.prepare(mediaSource)
        exoPlayer.player = mExoPlayer
        mExoPlayer.seekTo(playbackPosition)

        mExoPlayer.addListener(this)
        ivPause.setOnClickListener {
            mExoPlayer.playWhenReady = !mExoPlayer.isPlaying
            if (mExoPlayer.isPlaying)
                ivPause.setImageResource(R.drawable.ic_pause)
            else ivPause.setImageResource(R.drawable.ic_play)
        }
        ivRewind.setOnClickListener {
            mExoPlayer.seekTo(mExoPlayer.currentPosition - 10000)
        }
        ivSkip.setOnClickListener {
            mExoPlayer.seekTo(mExoPlayer.currentPosition + 30000)
        }
    }

    override fun showErrorSnackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun updateProgress(progress: Int, progressBar: ProgressBar) {
        progressBar.progress = progress
    }

    override fun onTapPodCastItem(podCastWrapper: PodCastWrapper) {
        startActivity(PodCastDetailActivity.newIntent(requireContext(), podCastWrapper))
    }

    override fun onTapDownload(url: String, progressBar: ProgressBar) {
        mPresenter.onDownload(url, requireActivity(),progressBar)
//        showErrorSnackBar("HEY SHOULD")
//        val downloadManager = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//        val uri = Uri.parse(url)
//        val request = DownloadManager.Request(uri)
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//        val id = downloadManager.enqueue(request)
//        val query = DownloadManager.Query()
//        query.setFilterById(id)
//        val cursor = downloadManager.query(query)
//        cursor.moveToFirst()
//        val bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
////        mView?.updateProgress(bytesDownloaded)
//        progressBar.progress = bytesDownloaded
//        cursor.close()
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        Snackbar.make(requireView(), error.localizedMessage ?: "", Snackbar.LENGTH_SHORT).show()
        Log.d("PLAYBACK", error.localizedMessage)
    }

    private fun releasePlayer() {
        playbackPosition = mExoPlayer.currentPosition
        mExoPlayer.release()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }
}