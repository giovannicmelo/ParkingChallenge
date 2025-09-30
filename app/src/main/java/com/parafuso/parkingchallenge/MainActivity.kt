package com.parafuso.parkingchallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.parafuso.parkingchallenge.core.presentation.viewBinding
import com.parafuso.parkingchallenge.databinding.ActivityMainBinding
import com.parafuso.parkingchallenge.feature.main.presentation.adapter.MainPageAdapter

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding(R.id.root)
    private val adapter by lazy { MainPageAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViews()
    }

    private fun setUpViews() {
        val tabLayout = binding.parkingTabLayout
        val viewPager = binding.parkingViewPager

        viewPager.adapter = MainPageAdapter(this)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getTabTitle(position)
        }.attach()
    }
}