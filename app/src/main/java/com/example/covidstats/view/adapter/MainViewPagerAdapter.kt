package com.example.covidstats.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.covidstats.view.fragment.*

//Adapter for main view pager
class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager,
    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    //numbers of options
    override fun getCount(): Int {
        return 4
    }

    //call fragment based on user selection
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AdviceFragment()
            1 -> VaccineCenterFragment()
            2 -> CurrentLocationFragment()
            else -> SearchFragment()
        }
    }
}