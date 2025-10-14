package com.parafuso.parkingchallenge.feature.main.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.parafuso.parkingchallenge.feature.parking.presentation.fragment.ParkingFragment
import com.parafuso.parkingchallenge.feature.parkingout.presentation.fragment.ParkingOutFragment

class MainPageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = Pages.entries.size

    fun getTabTitle(position: Int): String = Pages.entries[position].title

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            Pages.PARKING.ordinal -> ParkingFragment.newInstance()
            Pages.PARKING_OUT.ordinal -> ParkingOutFragment.newInstance()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    enum class Pages(val title: String) {

        PARKING("Entrada"),
        PARKING_OUT("Saída");
    }
}