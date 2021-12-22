package com.envercelik.doctorappointmentsystem.ui.doctorlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.envercelik.doctorappointmentsystem.databinding.FragmentDoctorListBinding
import com.envercelik.doctorappointmentsystem.ui.model.Doctor

class DoctorListFragment : Fragment() {
    private var _binding: FragmentDoctorListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DoctorListAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.recyclerViewDoctors.adapter = adapter
        binding.recyclerViewDoctors.layoutManager = layoutManager

        val doctor1 = Doctor("sds", "sdsd", "sdsd", "sdsd", "sdsd", "sds", "sd")
        val doctor2 = Doctor("sds", "sdsd", "sdsd", "sdsd", "sdsd", "sds", "sd")

        adapter.submitList(mutableListOf(doctor1, doctor2))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}