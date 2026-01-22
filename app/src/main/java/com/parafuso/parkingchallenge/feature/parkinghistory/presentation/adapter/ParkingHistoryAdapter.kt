package com.parafuso.parkingchallenge.feature.parkinghistory.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.parafuso.parkingchallenge.core.data.extension.formatTime
import com.parafuso.parkingchallenge.databinding.ItemParkingHistoryBinding
import com.parafuso.parkingchallenge.feature.parkinghistory.domain.model.ParkingHistory

class ParkingHistoryAdapter(
    private val onItemClicked: (ParkingHistory) -> Unit = {}
) : ListAdapter<ParkingHistory, ParkingHistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParkingHistoryViewHolder {
        return ParkingHistoryViewHolder.create(parent, onItemClicked)
    }

    override fun onBindViewHolder(holder: ParkingHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ParkingHistoryViewHolder(
    private val binding: ItemParkingHistoryBinding,
    private val onItemClicked: (ParkingHistory) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ParkingHistory) {
        binding.historyTimeValue.text = item.time.formatTime()
        binding.historyPaymentValue.text = if (item.paid) "Pago" else "-"
        binding.root.setOnClickListener { onItemClicked(item) }
    }

    companion object {
        fun create(parent: ViewGroup, onItemClicked: (ParkingHistory) -> Unit): ParkingHistoryViewHolder {
            val itemBinding = ItemParkingHistoryBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ParkingHistoryViewHolder(itemBinding, onItemClicked)
        }
    }
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ParkingHistory>() {
    override fun areItemsTheSame(
        oldItem: ParkingHistory,
        newItem: ParkingHistory
    ): Boolean {
        return oldItem.plate == newItem.plate
    }

    override fun areContentsTheSame(
        oldItem: ParkingHistory,
        newItem: ParkingHistory
    ): Boolean {
        return oldItem == newItem
    }
}