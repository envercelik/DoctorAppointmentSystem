package com.envercelik.doctorappointmentsystem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.envercelik.doctorappointmentsystem.databinding.FragmentUserSignupBinding

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}