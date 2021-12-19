package com.envercelik.doctorappointmentsystem.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.envercelik.doctorappointmentsystem.databinding.FragmentDoctorSignupBinding
import com.google.android.material.snackbar.Snackbar

class SignupDoctorFragment : Fragment() {
    private var _binding: FragmentDoctorSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignupDoctorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorSignupBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorState.observe(viewLifecycleOwner) {
            showErrorInSnackBar(it)
        }

        viewModel.navigateToDoctorProfileScreenState.observe(viewLifecycleOwner) {
            navigateToDoctorProfileScreen()
        }
    }

    private fun navigateToDoctorProfileScreen() {
        println("navigated")
    }

    private fun showErrorInSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}