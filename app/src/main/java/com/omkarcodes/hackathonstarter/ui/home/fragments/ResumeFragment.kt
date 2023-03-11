package com.omkarcodes.hackathonstarter.ui.home.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.omkarcodes.hackathonstarter.R
import com.omkarcodes.hackathonstarter.databinding.FragmentResumeBinding
import com.omkarcodes.hackathonstarter.ui.home.Feature
import com.omkarcodes.hackathonstarter.ui.home.HomeFragmentDirections
import com.omkarcodes.hackathonstarter.ui.home.adapters.FeaturesAdapter
import com.omkarcodes.hackathonstarter.ui.home.adapters.TopAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResumeFragment : Fragment(R.layout.fragment_resume){

    private var _binding: FragmentResumeBinding? = null
    private val binding: FragmentResumeBinding
        get() = _binding!!
    @Inject
    lateinit var pref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResumeBinding.bind(view)

        binding.apply {
            setupCarousel()
            rvFeatures.adapter = FeaturesAdapter(listOf(
                Feature("#bae5f4",R.drawable.ic_search,"Smart Search"),
                Feature("#e3d3fe",R.drawable.career,"Career fair"),
                Feature("#ccf1be",R.drawable.ic_mentor,"Mentorship"),
                Feature("#f0dfb4",R.drawable.ic_referral,"Referrals"),
                Feature("#f6b5e6",R.drawable.ic_contract,"Contracts"),
            ),object : FeaturesAdapter.OnClickListener {
                override fun onClick(position: Int) {
                    when(position) {
                        0 -> {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToSmartSearchFragment()
                            )
                        }
                        1 -> {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://hacker-world.glitch.me")))
                        }
                        2 -> {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToMentorshipFragment()
                            )
                        }
                        3 -> {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToReferralFragment()
                            )
                        }
                        4 -> {
                            findNavController().navigate(
                                HomeFragmentDirections.actionHomeFragmentToFreelanceFragment()
                            )
                        }
                    }
                }
            })
        }
    }

    private fun setupCarousel(){
        binding.apply {

            vpTop.adapter = TopAdapter()

            vpTop.offscreenPageLimit = 1

            val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
            val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
            val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                page.translationX = -pageTranslationX * position
                page.scaleY = 1 - (0.15f * kotlin.math.abs(position))
                page.alpha = 0.15f + (1 - kotlin.math.abs(position))
            }
            vpTop.setPageTransformer(pageTransformer)
            context?.let {
                val itemDecoration = HorizontalMarginItemDecoration(
                    it,
                    R.dimen.viewpager_current_item_horizontal_margin
                )
                vpTop.addItemDecoration(itemDecoration)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}