package com.example.ktmmoe.podcast.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.ktmmoe.podcast.R
import com.example.ktmmoe.podcast.adapters.recycleradapters.CategoryRecyclerAdapter
import com.example.ktmmoe.podcast.adapters.recycleradapters.PodCastRecyclerAdapter
import com.example.ktmmoe.podcast.data.vos.Genre
import com.example.ktmmoe.podcast.mvp.presenters.DownloadPresenter
import com.example.ktmmoe.podcast.mvp.presenters.SearchPresenter
import com.example.ktmmoe.podcast.mvp.presenters.impls.DownloadPresenterImpl
import com.example.ktmmoe.podcast.mvp.presenters.impls.SearchPresenterImpl
import com.example.ktmmoe.podcast.mvp.views.SearchView
import com.example.ktmmoe.shared.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment(), SearchView {
    private lateinit var mPresenter: SearchPresenter
    private lateinit var categoryRecyclerAdapter: CategoryRecyclerAdapter

    override fun setupPresenter() {
        mPresenter = ViewModelProviders.of(this).get(SearchPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPresenter()
        mPresenter.onCreate()
        mPresenter.onUiReady(this)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }

    override fun setupRecycler() {
        categoryRecyclerAdapter = CategoryRecyclerAdapter()
        recyclerview.adapter = categoryRecyclerAdapter
    }

    override fun displayCategoryList(genres: List<Genre>) {
        genreName.text = genres.first().name
        categoryRecyclerAdapter.setNewData(genres.toMutableList())
    }
}