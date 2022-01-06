package com.envercelik.doctorappointmentsystem.ui.doctorprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.envercelik.doctorappointmentsystem.R
import com.envercelik.doctorappointmentsystem.databinding.FragmentDoctorProfileBinding
import com.envercelik.doctorappointmentsystem.ui.model.Appointment
import com.envercelik.doctorappointmentsystem.ui.model.CurrentAppointment
import com.envercelik.doctorappointmentsystem.ui.model.Patient

class DoctorProfileFragment : Fragment() {

    private var _binding: FragmentDoctorProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentDoctorProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DoctorProfileCurrentAppointmentAdapter()
        binding.recyclerViewCurrentAppointments.adapter = adapter

        val currentAppointments = mutableListOf<CurrentAppointment>()

        val appointment1 = Appointment("sdsd", "sdsd", "12:00", true)
        val appointment2 = Appointment("aa", "aa", "13:00", true)

        val patient1 = Patient("sds", "patient1", "fdfdf", "Female", "dfdfd")
        val patient2 = Patient("aaa", "patient2", "aaa", "Male", "aa")

        val currentAppointment1 = CurrentAppointment(appointment1, patient1)
        val currentAppointment2 = CurrentAppointment(appointment2, patient2)

        currentAppointments.add(currentAppointment1)
        currentAppointments.add(currentAppointment2)

        adapter.submitList(currentAppointments)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_doctor, menu)
    }
}