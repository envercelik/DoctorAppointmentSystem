package com.envercelik.doctorappointmentsystem.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.envercelik.doctorappointmentsystem.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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

        viewModel.navigateToSignupScreenState.observe(viewLifecycleOwner) {
            navigateToSignup()
        }
    }

    private fun navigateToAppointmentScreen() {
        println("navigated")
    }

    private fun showErrorInSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun navigateToSignup() {
        val direction = LoginFragmentDirections.actionFragmentLoginToFragmentSignup()
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}