package com.envercelik.doctorappointmentsystem.ui.doctorprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.envercelik.doctorappointmentsystem.databinding.ItemAppointmentCurrentBinding
import com.envercelik.doctorappointmentsystem.ui.model.CurrentAppointment

class DoctorProfileCurrentAppointmentAdapter :
    ListAdapter<CurrentAppointment, DoctorProfileCurrentAppointmentAdapter.AppointmentHolder>
        (DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentHolder {
        val binding = ItemAppointmentCurrentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AppointmentHolder(binding)
    }

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) =
        holder.bind(getItem(position))

    class AppointmentHolder(private val binding: ItemAppointmentCurrentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CurrentAppointment) {
            binding.currentAppointment = item
            binding.executePendingBindings()
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<CurrentAppointment>() {

        override fun areItemsTheSame(oldItem: CurrentAppointment, newItem: CurrentAppointment) =
            oldItem.appointment.id == newItem.appointment.id


        override fun areContentsTheSame(oldItem: CurrentAppointment, newItem: CurrentAppointment) =
            oldItem == newItem
    }
}