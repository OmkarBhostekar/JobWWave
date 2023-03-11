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
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import com.omkarcodes.hackathonstarter.databinding.FragmentProfileBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentSmartSearchBinding
import com.omkarcodes.hackathonstarter.ui.usp.adapters.SmartSearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SmartSearchFragment : Fragment(R.layout.fragment_smart_search){

    private var _binding: FragmentSmartSearchBinding? = null
    private val binding: FragmentSmartSearchBinding
        get() = _binding!!
    @Inject
    lateinit var pref: SharedPreferences

    private val viewModel: UspViewModel by viewModels()
    private val companies = mutableListOf<String>()
    private val skills = mutableListOf<String>()
    private val titles = mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSmartSearchBinding.bind(view)

        binding.apply {

            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnAddC.setOnClickListener {
                if(etSearch.text.isNotEmpty()){
                    addChip(cg1, etSearch.text.toString(), 1)
                    companies.add(etSearch.text.toString())
                    etSearch.setText("")
                }
            }

            btnAddSkill.setOnClickListener {
                if(etSkills.text.isNotEmpty()){
                    addChip(cg2, etSkills.text.toString(), 2)
                    skills.add(etSearch.text.toString())
                    etSkills.setText("")
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
                if (companies.size < 2 || skills.size < 2 && titles.size < 2){
                    Toast.makeText(context, "Add at least 2 values for each category", Toast.LENGTH_SHORT).show()
                }else{
                    viewModel.getSmartSearch(companies, skills, titles)
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

            viewModel.onSearchResult().observe(viewLifecycleOwner) {
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
        }
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
                2 -> skills.remove(str)
                3 -> titles.remove(str)
            }
        }
        cg1.addView(chip)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}