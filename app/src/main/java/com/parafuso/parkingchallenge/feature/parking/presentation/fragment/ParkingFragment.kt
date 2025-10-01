package com.parafuso.parkingchallenge.feature.parking.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.parafuso.parkingchallenge.R
import com.parafuso.parkingchallenge.core.presentation.viewBinding
import com.parafuso.parkingchallenge.databinding.FragmentParkingBinding

class ParkingFragment : Fragment() {

    private val binding: FragmentParkingBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_parking, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
        binding.plateInput.requestFocus()
    }

    private fun setUpViews() {
        // TODO : Will be implemented
    }

    companion object {
        fun newInstance() = ParkingFragment()
    }
}