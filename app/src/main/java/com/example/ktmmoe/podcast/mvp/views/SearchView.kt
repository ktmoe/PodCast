package com.example.ktmmoe.podcast.mvp.views

import com.example.ktmmoe.podcast.data.vos.Genre
import com.example.ktmmoe.shared.mvp.views.BaseView

/**
 * Created by ktmmoe on 30, August, 2020
 **/
interface SearchView: BaseView {
    fun setupRecycler()
    fun displayCategoryList(genres: List<Genre>)
}