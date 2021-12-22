package com.envercelik.doctorappointmentsystem.ui.doctorlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.envercelik.doctorappointmentsystem.databinding.ItemDoctorBinding
import com.envercelik.doctorappointmentsystem.ui.model.Doctor


class DoctorListAdapter : ListAdapter<Doctor, DoctorListAdapter.DoctorHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorHolder {
        val binding = ItemDoctorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DoctorHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorHolder, position: Int) =
        holder.bind(getItem(position))

    class DoctorHolder(private val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Doctor) {
            binding.doctor = item
            binding.executePendingBindings()
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Doctor>() {

        override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor) =
            oldItem.uid == newItem.uid


        override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor) =
            oldItem == newItem
    }
}