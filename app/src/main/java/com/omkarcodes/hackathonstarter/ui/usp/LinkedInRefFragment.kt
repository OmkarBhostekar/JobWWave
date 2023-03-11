package com.omkarcodes.hackathonstarter.ui.usp

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.common.Resource
import com.omkarcodes.hackathonstarter.common.openDialog
import com.omkarcodes.hackathonstarter.data.model.JobRef
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import com.omkarcodes.hackathonstarter.databinding.FragmentLinkedinRefBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentProfileBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentSmartSearchBinding
import com.omkarcodes.hackathonstarter.ui.usp.adapters.RefAdapter
import com.omkarcodes.hackathonstarter.ui.usp.adapters.SmartSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LinkedInRefFragment : Fragment(R.layout.fragment_linkedin_ref){

    private var _binding: FragmentLinkedinRefBinding? = null
    private val binding: FragmentLinkedinRefBinding
        get() = _binding!!
    @Inject
    lateinit var pref: SharedPreferences

    private val viewModel: UspViewModel by viewModels()
    private val companies = mutableListOf<String>()
    private val titles = mutableListOf<String>()
    private var isLinkedIn = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLinkedinRefBinding.bind(view)

        binding.apply {

            if(!isLinkedIn){
                searchBox.visibility = View.GONE
                showFilters.visibility = View.GONE
                viewModel.getJobWwaveRef()
            }

            btnAddC.setOnClickListener {
                if(etSearch.text.isNotEmpty()){
                    addChip(cg1, etSearch.text.toString(), 1)
                    companies.add(etSearch.text.toString())
                    etSearch.setText("")
                }
            }

            btnAddTitle.setOnClickListener {
                if(etSearchT.text.isNotEmpty()){
                    addChip(cg3, etSearchT.text.toString(), 3)
                    titles.add(etSearch.text.toString())
                    etSearchT.setText("")
                }
            }

            btnSearch.setOnClickListener {
                if (companies.size < 2  || titles.size < 2){
                    Toast.makeText(context, "Add at least 2 values for each category", Toast.LENGTH_SHORT).show()
                }else{
                    if(isLinkedIn)
                        viewModel.getLinkedInRef(companies, titles)
                    searchBox.visibility = View.GONE
                    showFilters.visibility = View.VISIBLE
                    rvSmartSearch.visibility = View.VISIBLE
                }
            }

            hideFilters.setOnClickListener {
                searchBox.visibility = View.GONE
                showFilters.visibility = View.VISIBLE
                rvSmartSearch.visibility = View.VISIBLE
            }

            showFilters.setOnClickListener {
                searchBox.visibility = View.VISIBLE
                showFilters.visibility = View.GONE
                rvSmartSearch.visibility = View.GONE
            }

            viewModel.onLinkedInResult().observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        rvSmartSearch.adapter = it.data?.let { it1 ->
                            SmartSearchAdapter(it1,object : SmartSearchAdapter.OnClickListener {
                                override fun onClick(result: SearchResult) {
                                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(result.link)))
                                }
                            })
                        }
                    }
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    else -> Unit
                }
            }

            viewModel.onJobWwaveResult().observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        rvSmartSearch.visibility = View.VISIBLE
                        rvSmartSearch.adapter = it.data?.let { it1 ->
                            RefAdapter(it1, object : RefAdapter.OnClickListener {
                                override fun onClick(result: JobRef) {
                                    pref.getString("userId","")
                                        ?.let { it2 -> launchDialogFragment(it2, result._id) }
                                }
                            })
                        }
                    }
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun launchDialogFragment(myId: String, otherId: String) {
        val dialog = DialogRefFragment()
        dialog.setUserId(myId, otherId)
        activity?.openDialog(dialog,DialogRefFragment.TAG)
    }

    private fun addChip(cg1: ChipGroup, str: String, type: Int) {
        val chip = Chip(cg1.context)
        chip.text = str
        chip.textSize = 14f
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            cg1.removeView(chip)
            when(type){
                1 -> companies.remove(str)
                3 -> titles.remove(str)
            }
        }
        cg1.addView(chip)
    }

    fun newInstance(isLinkedIn: Boolean): Fragment {
        val args = Bundle()
        val fragment = this
        this.isLinkedIn = isLinkedIn
        fragment.arguments = args
        return fragment
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}