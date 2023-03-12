package com.omkarcodes.hackathonstarter.ui.usp

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.common.Resource
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import com.omkarcodes.hackathonstarter.databinding.FragmentProfileBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentReferralBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentSmartSearchBinding
import com.omkarcodes.hackathonstarter.ui.home.adapters.ViewPager2Adapter
import com.omkarcodes.hackathonstarter.ui.usp.adapters.SmartSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReferralFragment : Fragment(R.layout.fragment_referral){

    private var _binding: FragmentReferralBinding? = null
    private val binding: FragmentReferralBinding
        get() = _binding!!
    @Inject
    lateinit var pref: SharedPreferences

    private val viewModel: UspViewModel by viewModels()
    private val companies = mutableListOf<String>()
    private val skills = mutableListOf<String>()
    private val titles = mutableListOf<String>()
    private var isLinkedInSelected = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReferralBinding.bind(view)

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
            btnInternal.setOnClickListener {
                isLinkedInSelected = false
                btnInternal.setBackgroundColor(Color.BLACK)
                btnInternal.setTextColor(Color.WHITE)
                btnLinkedIn.setBackgroundColor(Color.WHITE)
                btnLinkedIn.setTextColor(Color.BLACK)
                viewpager.setCurrentItem(1,true)
            }
            btnLinkedIn.setOnClickListener {
                isLinkedInSelected = true
                btnLinkedIn.setBackgroundColor(Color.BLACK)
                btnLinkedIn.setTextColor(Color.WHITE)
                btnInternal.setBackgroundColor(Color.WHITE)
                btnInternal.setTextColor(Color.BLACK)
                viewpager.setCurrentItem(0,true)
            }
            viewpager.adapter = activity?.let { ViewPager2Adapter(it, listOf(
                LinkedInRefFragment().newInstance(true),
                LinkedInRefFragment().newInstance(false)
            ))}
            viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when(position){
                        0 -> {
                            isLinkedInSelected = true
                            btnLinkedIn.setBackgroundColor(Color.BLACK)
                            btnLinkedIn.setTextColor(Color.WHITE)
                            btnInternal.setBackgroundColor(Color.WHITE)
                            btnInternal.setTextColor(Color.BLACK)
                        }
                        1 -> {
                            isLinkedInSelected = false
                            btnInternal.setBackgroundColor(Color.BLACK)
                            btnInternal.setTextColor(Color.WHITE)
                            btnLinkedIn.setBackgroundColor(Color.WHITE)
                            btnLinkedIn.setTextColor(Color.BLACK)
                        }
                    }
                }
            })


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}