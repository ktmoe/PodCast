package com.example.ktmmoe.podcast.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktmmoe.podcast.R
import com.example.ktmmoe.podcast.activities.PodCastDetailActivity
import com.example.ktmmoe.podcast.adapters.recycleradapters.PodCastRecyclerAdapter
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.mvp.presenters.DownloadPresenter
import com.example.ktmmoe.podcast.mvp.presenters.impls.DownloadPresenterImpl
import com.example.ktmmoe.podcast.mvp.views.DownloadView
import com.example.ktmmoe.shared.fragments.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_download.*

class DownloadFragment : BaseFragment(), DownloadView {
    private lateinit var mPresenter: DownloadPresenter
    private lateinit var podCastRecyclerAdapter: PodCastRecyclerAdapter

    override fun setupPresenter() {
        mPresenter = ViewModelProviders.of(this).get(DownloadPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_download, container, false)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.onUiReady(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPresenter()
        mPresenter.onCreate()
    }

    companion object {
        @JvmStatic
        fun newInstance() = DownloadFragment()
    }

    override fun setupRecycler() {
        podCastRecyclerAdapter = PodCastRecyclerAdapter(this, true)
        recyclerview.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerview.adapter = podCastRecyclerAdapter
    }

    override fun displayPodCastList(podCasts: List<PodCastWrapper>) {
        emptyDownloadList.visibility = if (podCasts.isEmpty()) View.VISIBLE else View.GONE
        recyclerview.visibility = if (podCasts.isNotEmpty()) View.VISIBLE else View.GONE
        podCastRecyclerAdapter.setNewData(podCasts.toMutableList())
    }

    override fun showErrorSnackBar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onTapPodCastItem(podCastWrapper: PodCastWrapper) {
        startActivity(PodCastDetailActivity.newIntent(requireContext(), podCastWrapper, true))
    }

    override fun onTapDownload(podCast: PodCastWrapper, itemView: View) {

    }
}