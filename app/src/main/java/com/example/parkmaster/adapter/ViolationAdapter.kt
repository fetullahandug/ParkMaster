package com.example.parkmaster.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parkmaster.data.model.Violation
import com.example.parkmaster.databinding.ViolationItemBinding

class ViolationAdapter(private val dataset: List<Violation>) : RecyclerView.Adapter<ViolationAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ViolationItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ViolationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val violation = dataset[position]

        holder.binding.tvVehicleId.text = violation.vehicle_id
        holder.binding.tvAddress.text = "${violation.address} ${violation.housenumber}, ${violation.postcode} ${violation.city}"
        holder.binding.tvDate.text = violation.date.toString()
        holder.binding.tvTime.text = violation.time
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}