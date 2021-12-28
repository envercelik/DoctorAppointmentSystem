package com.envercelik.doctorappointmentsystem.ui.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.envercelik.doctorappointmentsystem.databinding.FragmentAppointmentBinding
import com.envercelik.doctorappointmentsystem.ui.model.Appointment
import com.envercelik.doctorappointmentsystem.ui.model.Doctor

class FragmentAppointment : Fragment() {
    private var _binding: FragmentAppointmentBinding? = null
    private val binding get() = _binding!!
    private val args: FragmentAppointmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        binding.doctor = Doctor(nameSurname = "Enver Çelik", clinic = "Noroloji")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AppointmentAdapter()
        val layoutManager = StaggeredGridLayoutManager(3, VERTICAL)

        binding.recyclerViewAppointmentList.adapter = adapter
        binding.recyclerViewAppointmentList.layoutManager = layoutManager

        val appointmentList = mutableListOf<Appointment>()
        appointmentList.add(Appointment("www", "xx", "12:00", true))
        appointmentList.add(Appointment("www", "xx", "13:00", true))
        appointmentList.add(Appointment("www", "xx", "14:00", true))

        adapter.submitList(appointmentList)

        Toast.makeText(requireContext(), args.doctorUid, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}