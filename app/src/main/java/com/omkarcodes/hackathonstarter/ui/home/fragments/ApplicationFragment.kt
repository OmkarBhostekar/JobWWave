package com.omkarcodes.hackathonstarter.ui.home.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.databinding.FragmentApplicationsBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ApplicationFragment : Fragment(R.layout.fragment_applications){

    private var _binding: FragmentApplicationsBinding? = null
    private val binding: FragmentApplicationsBinding
        get() = _binding!!
    @Inject
    lateinit var pref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentApplicationsBinding.bind(view)

        binding.apply {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}