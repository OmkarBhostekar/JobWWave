package com.omkarcodes.hackathonstarter.ui.home.adapters

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(
    fragmentActivity: FragmentActivity,
    private var fragmentList: List<Fragment>
) :
    FragmentStateAdapter(fragmentActivity) {


    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    fun getFragments(): List<Fragment> {
        return fragmentList
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }
}