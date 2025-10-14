package com.parafuso.parkingchallenge.feature.parking.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import com.parafuso.parkingchallenge.R
import com.parafuso.parkingchallenge.core.presentation.viewBinding
import com.parafuso.parkingchallenge.databinding.FragmentParkingBinding
import com.parafuso.parkingchallenge.feature.parking.presentation.viewmodel.ParkingViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ParkingFragment : Fragment() {

    private val binding: FragmentParkingBinding by viewBinding()
    private val viewModel: ParkingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_parking, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        configureObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.confirmButton.setOnClickListener {
            viewModel.doParking()
        }
    }

    private fun configureObservers() {
        viewModel.viewState.asLiveData().observe(viewLifecycleOwner) { viewState ->
            binding.apply {
                confirmButton.isEnabled = viewState.isValidPlate
                parkingContainer.isInvisible = viewState.isLoading
                loaderContainer.isVisible = viewState.isLoading
                successContainer.isVisible = viewState.parking != null

                if (viewState.isError) {
                    errorContainer.isVisible = true
                    errorMessage.text = viewState.errorMessage
                    context?.run { plateInput.setTextColor(getColor(R.color.error)) }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
        binding.plateInput.requestFocus()
    }

    private fun setUpViews() {
        binding.plateInput.doAfterTextChanged { editable ->
            viewModel.validatePlate(editable.toString())
        }
    }

    companion object {
        fun newInstance() = ParkingFragment()
    }
}