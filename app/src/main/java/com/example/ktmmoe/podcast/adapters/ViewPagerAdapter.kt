package com.example.ktmmoe.podcast.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ktmmoe.podcast.fragments.DownloadFragment
import com.example.ktmmoe.podcast.fragments.HomeFragment
import com.example.ktmmoe.podcast.fragments.ProfileFragment
import com.example.ktmmoe.podcast.fragments.SearchFragment


/**
 * Created by ktmmoe on 30, August, 2020
 **/
class ViewPagerAdapter(private val fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment = when(position) {
        0 -> HomeFragment.newInstance()
        1 -> SearchFragment.newInstance()
        2 -> DownloadFragment.newInstance()
        else -> ProfileFragment.newInstance()
    }
}