package com.envercelik.doctorappointmentsystem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.envercelik.doctorappointmentsystem.databinding.FragmentUserSignupBinding
import com.google.android.material.snackbar.Snackbar

class SignupUserFragment : Fragment(R.layout.fragment_user_signup) {
    private var _binding: FragmentUserSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignupUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSignupBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorState.observe(viewLifecycleOwner) {
            showErrorInSnackBar(it)
        }

        viewModel.navigateToAppointmentScreenState.observe(viewLifecycleOwner) {
            navigateToAppointmentScreen()
        }
    }

    private fun navigateToAppointmentScreen() {
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