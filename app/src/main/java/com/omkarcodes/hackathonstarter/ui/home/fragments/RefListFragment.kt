package com.omkarcodes.hackathonstarter.ui.home.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.common.Resource
import com.omkarcodes.hackathonstarter.databinding.FragmentHomeBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentProfileBinding
import com.omkarcodes.hackathonstarter.databinding.FragmentRefListBinding
import com.omkarcodes.hackathonstarter.ui.home.HomeViewModel
import com.omkarcodes.hackathonstarter.ui.home.adapters.RefListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RefListFragment : Fragment(R.layout.fragment_ref_list){

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
            pref.getString("userId",null)?.let { viewModel.getRefList(it) }
            viewModel.onReferralList().observe(viewLifecycleOwner) {
                if(it is Resource.Success){
                    it.data?.let {
                        rvList.adapter = RefListAdapter(it, object : RefListAdapter.OnClickListener {
                            override fun onCopy(msg: String) {
                                if(msg.isNotEmpty()){
                                    activity?.let { activity ->
                                        val clipboardManager = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clipData = ClipData.newPlainText("text", msg)
                                        clipboardManager.setPrimaryClip(clipData)
                                        Toast.makeText(activity, "Text copied to clipboard", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        })
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