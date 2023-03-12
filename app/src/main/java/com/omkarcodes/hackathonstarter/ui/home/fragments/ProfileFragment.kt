package com.omkarcodes.hackathonstarter.ui.home.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.common.Resource
import com.omkarcodes.hackathonstarter.databinding.FragmentHomeBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentProfileBinding
import com.omkarcodes.hackathonstarter.ui.home.HomeFragmentDirections
import com.omkarcodes.hackathonstarter.ui.home.HomeViewModel
import com.omkarcodes.hackathonstarter.ui.usp.adapters.ChipsAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile){

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding!!
    @Inject
    lateinit var pref: SharedPreferences
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        binding.apply {
            pref.getString("userId",null)?.let {uid ->
                viewModel.getProfile(uid)
            }
            btnLogout.setOnClickListener {
                pref.edit()
                    .putString("token","")
                    .putString("userId","")
                    .putString("name","")
                    .apply()

                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToLoginFragment()
                )
            }
            viewModel.onProfile().observe(viewLifecycleOwner) {
                if (it is Resource.Success) {
                    it.data?.let { p ->
                        tvName.text = "${p.firstname} ${p.lastname}"
                        tvEmail.text = p.email
                        tvMobile.text = p.mobilenum
                        tvExp.text = "${p.experience} Years"
                        rvJt.adapter = ChipsAdapter(p.jobTitle)
                        tvLayoff.text = if (p.layoff) "Yes" else "No"
                        rvSkills.adapter = ChipsAdapter(p.skills)
                        tvLocation.text = p.location
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}