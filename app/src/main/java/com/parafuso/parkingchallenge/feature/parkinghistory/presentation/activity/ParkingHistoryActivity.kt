package com.parafuso.parkingchallenge.feature.parkinghistory.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import com.parafuso.parkingchallenge.R
import com.parafuso.parkingchallenge.core.presentation.viewBinding
import com.parafuso.parkingchallenge.databinding.ActivityParkingHistoryBinding
import com.parafuso.parkingchallenge.feature.parkinghistory.presentation.adapter.ParkingHistoryAdapter
import com.parafuso.parkingchallenge.feature.parkinghistory.presentation.viewmodel.ParkingHistoryViewModel
import kotlinx.parcelize.Parcelize
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ParkingHistoryActivity : AppCompatActivity() {

    private val args: Args by lazy {
        intent.extras?.getParcelable(TAG) ?: Args("")
    }

    private val binding: ActivityParkingHistoryBinding by viewBinding(
        viewBindingRootId = R.id.history_root
    )

    private val viewModel: ParkingHistoryViewModel by viewModel {
        parametersOf(args)
    }

    private val adapter: ParkingHistoryAdapter by lazy {
        ParkingHistoryAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_history)
        setUpViews()
        configureObservers()
        setListeners()
    }

    private fun setUpViews() {
        with(binding) {
            historyRecyclerView.adapter = adapter
            plateLabel.text = getString(R.string.plate, args.plate)
        }
    }

    private fun configureObservers() {
        viewModel.viewState.asLiveData().observe(this) { state ->
            binding.loaderContainer.isVisible = state.isLoading
            binding.historyRecyclerView.isVisible = state.isLoading.not()
            adapter.submitList(state.parkingHistory)
        }
    }

    private fun setListeners() {
        binding.backButton.setOnClickListener { finish() }
    }

    @Parcelize
    data class Args(val plate: String): Parcelable

    companion object {
        const val TAG = "ParkingHistoryActivity"

        fun createIntent(context: Context, args: Args) = Intent(
            context,
            ParkingHistoryActivity::class.java
        ).apply {
            putExtra(TAG, args)
        }
    }
}