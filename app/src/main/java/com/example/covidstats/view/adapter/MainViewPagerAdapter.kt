package com.example.covidstats.view.adapter

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.covidstats.util.MenuItems
import com.example.covidstats.view.fragment.ApplicatonFragment
import com.example.covidstats.view.fragment.CurrentLocationFragment
import com.example.covidstats.view.fragment.SearchFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager,     FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0->  CurrentLocationFragment()
            1-> SearchFragment()
            2-> ApplicatonFragment.getInstance(MenuItems.VACCINE)
            else ->ApplicatonFragment.getInstance(MenuItems.POSTS)
        }
    }
}