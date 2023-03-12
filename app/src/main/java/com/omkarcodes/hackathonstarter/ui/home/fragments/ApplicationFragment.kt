package com.omkarcodes.hackathonstarter.ui.home.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.databinding.FragmentHomeBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentProfileBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentRefListBinding
import com.omkarcodes.hackathonstarter.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ApplicationFragment : Fragment(R.layout.fragment_ref_list){

    private var _binding: FragmentRefListBinding? = null
    private val binding: FragmentRefListBinding
        get() = _binding!!
    @Inject
    lateinit var pref: SharedPreferences

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRefListBinding.bind(view)

        binding.apply {
            pref.getString("userId",null)?.let { viewModel.getApplications(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}