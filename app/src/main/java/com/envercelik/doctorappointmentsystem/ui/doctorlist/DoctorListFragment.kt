package com.envercelik.doctorappointmentsystem.ui.doctorlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.envercelik.doctorappointmentsystem.databinding.FragmentDoctorListBinding
import com.google.android.material.snackbar.Snackbar

class DoctorListFragment : Fragment() {
    private var _binding: FragmentDoctorListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DoctorListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorListBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DoctorListAdapter()
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.recyclerViewDoctors.adapter = adapter
        binding.recyclerViewDoctors.layoutManager = layoutManager

        adapter.itemClickListener = {
            navigateToAppointmentScreen(it.uuid)
        }

        viewModel.doctors.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showErrorInSnackBar(it)
        }
    }

    private fun navigateToAppointmentScreen(uid: String) {
        val directions =
            DoctorListFragmentDirections.actionDoctorListFragmentToFragmentAppointment(uid)
        findNavController().navigate(directions)
    }

    private fun showErrorInSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}