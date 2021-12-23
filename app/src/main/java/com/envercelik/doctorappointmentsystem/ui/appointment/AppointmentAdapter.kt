package com.envercelik.doctorappointmentsystem.ui.appointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.envercelik.doctorappointmentsystem.databinding.ItemAppointmentBinding
import com.envercelik.doctorappointmentsystem.ui.model.Appointment

class AppointmentAdapter :
    ListAdapter<Appointment, AppointmentAdapter.AppointmentHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
        val binding = ItemAppointmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AppointmentHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) =
        holder.bind(getItem(position))

    class AppointmentHolder(private val binding: ItemAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Appointment) {
            binding.appointment = item
            binding.executePendingBindings()
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Appointment>() {

        override fun areItemsTheSame(oldItem: Appointment, newItem: Appointment) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Appointment, newItem: Appointment) =
            oldItem == newItem
    }
}