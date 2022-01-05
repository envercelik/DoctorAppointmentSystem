package com.envercelik.doctorappointmentsystem.ui.doctordetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.envercelik.doctorappointmentsystem.databinding.FragmentDoctorDetailBinding

class DoctorDetailFragment : Fragment() {
    private var _binding: FragmentDoctorDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DoctorDetailFragmentArgs by navArgs()
    private val viewModel: DoctorDetailViewModel by viewModels {
        DoctorDetailViewModelFactory(args.uid)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }
}