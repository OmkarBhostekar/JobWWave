package com.omkarcodes.hackathonstarter.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.messaging.FirebaseMessaging
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.databinding.FragmentHomeBinding
import com.omkarcodes.hackathonstarter.ui.chat.ChatFragment
import com.omkarcodes.hackathonstarter.ui.chat.SingleChatActivity
import com.omkarcodes.hackathonstarter.ui.home.adapters.ViewPager2Adapter
import com.omkarcodes.hackathonstarter.ui.home.fragments.ApplicationFragment
import com.omkarcodes.hackathonstarter.ui.home.fragments.ProfileFragment
import com.omkarcodes.hackathonstarter.ui.home.fragments.RefListFragment
import com.omkarcodes.hackathonstarter.ui.home.fragments.ResumeFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home){

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!
    @Inject
    lateinit var pref: SharedPreferences
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        binding.apply {
            pref.getString("userId",null)?.let { uid ->
                FirebaseMessaging.getInstance()
                    .token
                    .addOnSuccessListener { token ->
                        viewModel.pushToken(uid, token)
                    }
            }
            Log.d("config",pref.getString("userId",null).toString())
            Log.d("config",pref.getString("token",null).toString())
            Log.d("config",pref.getString("name",null).toString())
            val screens = listOf(
                ResumeFragment(),
                ApplicationFragment(),
                RefListFragment(),
                ProfileFragment(),
            )
            vpHome.adapter = activity?.let { ViewPager2Adapter(it,screens) }
            vpHome.isUserInputEnabled = false
            vpHome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
            bottomNav.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.miResume -> {
                        vpHome.setCurrentItem(0,true)
                    }
                    R.id.miApplications -> {
                        vpHome.setCurrentItem(1,true)
                    }
                    R.id.miInbox -> {
                        vpHome.setCurrentItem(2,true)
                    }
                    R.id.miProfile -> {
                        vpHome.setCurrentItem(3,true)
                    }
                }
                true
            }
        }
    }

     override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}