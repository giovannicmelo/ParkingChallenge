package com.parafuso.parkingchallenge.feature.main.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPageAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val tabTitles = listOf("Entrada", "Saída")

    override fun getItemCount(): Int = tabTitles.size

    fun getTabTitle(position: Int): String = tabTitles[position]

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Fragment()
            1 -> Fragment()
            else -> throw IllegalArgumentException("Posição inválida")
        }
    }
}