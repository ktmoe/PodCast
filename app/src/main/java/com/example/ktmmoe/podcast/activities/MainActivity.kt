package com.example.ktmmoe.podcast.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.example.ktmmoe.podcast.R
import com.example.ktmmoe.podcast.adapters.ViewPagerAdapter
import com.example.ktmmoe.podcast.mvp.presenters.MainPresenter
import com.example.ktmmoe.podcast.mvp.presenters.impls.MainPresenterImpl
import com.example.ktmmoe.podcast.mvp.views.MainView
import com.example.ktmmoe.shared.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {
    private lateinit var mPresenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpPresenter()
        mPresenter.onCreate()
    }

    override fun setUpPresenter() {
        mPresenter = ViewModelProviders.of(this).get(MainPresenterImpl::class.java)
        mPresenter.initPresenter(this)
    }

    override fun setupViewPager() {
        viewpager.adapter = ViewPagerAdapter(this)
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNavigation.menu.getItem(position).isChecked = true
            }
        })
    }

    override fun setupBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> viewpager.setCurrentItem(0, true)
                R.id.menuSearch -> viewpager.setCurrentItem(1, true)
                R.id.menuDownload -> viewpager.setCurrentItem(2, true)
                R.id.menuProfile -> viewpager.setCurrentItem(3, true)
            }
            true
        }
    }
}