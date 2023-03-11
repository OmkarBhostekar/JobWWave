package com.omkarcodes.hackathonstarter.ui.auth.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.databinding.FragmentOnBoardingBinding
import com.omkarcodes.hackathonstarter.ui.auth.adapters.OnboardingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingFragment : Fragment(R.layout.fragment_on_boarding){

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding: FragmentOnBoardingBinding
        get() = _binding!!
    @Inject
    lateinit var pref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOnBoardingBinding.bind(view)

        binding.apply {

            if(pref.getString("token",null) != null){
                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment())
            }

            vpOnboarding.adapter = OnboardingAdapter()
            dotIndicator.setViewPager2(vpOnboarding)

            btnSignUp.setOnClickListener {
                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToSignUpFragment())
            }

            btnLogin.setOnClickListener {
                findNavController().navigate(OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment())
            }

            lifecycleScope.launch {
                while (true){
                    delay(3000L)
                    if (vpOnboarding.currentItem == 2){
                        vpOnboarding.setCurrentItem(0,true)
                    }else vpOnboarding.setCurrentItem(vpOnboarding.currentItem +1, true)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // TODO: check if user is logged in
//        if (){
//
//        }
    }

     override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}