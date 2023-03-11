package com.omkarcodes.hackathonstarter.ui.usp

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.common.Resource
import com.omkarcodes.hackathonstarter.data.model.search.SearchResult
import com.omkarcodes.hackathonstarter.databinding.FragmentFreelanceBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentHomeBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentProfileBinding
import com.omkarcodes.hackathonstarter.ui.usp.adapters.SmartSearchAdapter
import com.pluto.plugins.logger.PlutoLog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FreelanceFragment : Fragment(R.layout.fragment_freelance){

    private var _binding: FragmentFreelanceBinding? = null
    private val binding: FragmentFreelanceBinding
        get() = _binding!!
    @Inject
    lateinit var pref: SharedPreferences
    private val viewModel: UspViewModel by viewModels()
//    val timer: Timer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFreelanceBinding.bind(view)

        binding.apply {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            val mTextWatcher: TextWatcher = object : TextWatcher {
                private var timer = Timer()
                private val DELAY: Long = 1000L

                override fun afterTextChanged(s: Editable?) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(object : TimerTask() {
                        override fun run() {
                            if (!etSearch.text.isNullOrEmpty()){
                                viewModel.getFree(etSearch.text.toString())
                            }
                        }
                    }, DELAY)
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

            }
            etSearch.addTextChangedListener(mTextWatcher)
            viewModel.getFree("Website")
            viewModel.onFreeResult().observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Success -> {
                        it.data?.let {
                            rvFree.adapter = SmartSearchAdapter(it,object : SmartSearchAdapter.OnClickListener {
                                override fun onClick(result: SearchResult) {
                                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(result.link)))
                                }
                            }, isFreelancing = true)
                            PlutoLog.d("",it.toString())
                        }
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