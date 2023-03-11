package com.omkarcodes.hackathonstarter.ui.usp

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.omkarcodes.hackathonstarter.common.Resource
import com.omkarcodes.hackathonstarter.databinding.DialogRefBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogRefFragment : DialogFragment() {

    companion object {
        const val TAG = "DialogRefFragment"
    }

    private var binding: DialogRefBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogRefBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    private val viewModel: UspViewModel by viewModels()
    private var userId = ""
    private var otherId = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dialog?.let {
            it.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            it.window!!.setBackgroundDrawable(ColorDrawable(0))
        }

        isCancelable = false

        binding?.apply {
            btnBack.setOnClickListener { dismiss() }
            viewModel.getReferralMessage(userId)
            viewModel.onRefMsg().observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        etMessage.visibility = View.VISIBLE
                        it.data?.let { msg -> etMessage.setText(msg)}
                    }
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        etMessage.visibility = View.INVISIBLE
                    }
                    else -> Unit
                }
            }
            viewModel.onRefMsgSent().observe(viewLifecycleOwner) {
                when(it){
                    is Resource.Success -> {
                        dismiss()
                    }
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        etMessage.visibility = View.INVISIBLE
                    }
                    else -> Unit
                }
            }
            btnSearch.setOnClickListener {
                if(etMessage.text.toString().isNotEmpty()){
                    viewModel.sendReferralMessage(userId, otherId, etMessage.text.toString())
                }
            }
        }

    }

    fun setUserId(myId: String, o: String){
        userId = myId
        otherId = o
    }
}