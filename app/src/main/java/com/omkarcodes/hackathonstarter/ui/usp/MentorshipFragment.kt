package com.omkarcodes.hackathonstarter.ui.usp

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.common.Resource
import com.omkarcodes.hackathonstarter.data.model.Mentor
import com.omkarcodes.hackathonstarter.databinding.FragmentHomeBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentMentorshipBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentProfileBinding
import com.omkarcodes.hackathonstarter.ui.home.HomeFragmentDirections
import com.omkarcodes.hackathonstarter.ui.usp.adapters.MentorAdapter
import com.pluto.plugins.logger.PlutoLog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MentorshipFragment : Fragment(R.layout.fragment_mentorship){

    private var _binding: FragmentMentorshipBinding? = null
    private val binding: FragmentMentorshipBinding
        get() = _binding!!
    @Inject
    lateinit var pref: SharedPreferences
    private val viewModel: UspViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMentorshipBinding.bind(view)

        binding.apply {
            btnBack.setOnClickListener { findNavController().popBackStack() }
            viewModel.fetchMentors()
            viewModel.onMentors().observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Success -> {
                        Log.d("mentors",it.data.toString())
                        progressBar.visibility = View.GONE
                        rvMentors.adapter = it.data?.let { it1 -> MentorAdapter(it1, object : MentorAdapter.OnClickListener {
                            override fun onClick(result: Mentor) {
                                val user = pref.getString("name","")
                                findNavController().navigate(
                                    MentorshipFragmentDirections.actionMentorshipFragmentToWebViewFragment(
                                        "https://ossified-same-koi.glitch.me/chat.html?user=${user?.toLowerCase()}"
                                    )
                                )
                            }
                        }) }
                    }
                    is Resource.Loading -> {

                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}