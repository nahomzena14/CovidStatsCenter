package com.example.covidstats.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.covidstats.R
import com.example.covidstats.util.MenuItems

class ApplicatonFragment:Fragment() {

    companion object{
        const val KEY = "KEY"
        fun getInstance(fragment: MenuItems): ApplicatonFragment {
            return ApplicatonFragment().also { frag ->
                frag.arguments = Bundle().also {
                    it.putString(KEY, fragment.name)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view: View?= null
        arguments?.getString(KEY)?.let {
            view = when(MenuItems.valueOf(it)){
                MenuItems.CURRENT_LOCATION -> inflater.inflate(R.layout.current_location_fragment_layout,container,false)
                MenuItems.SEARCH -> inflater.inflate(R.layout.search_fragment_layout,container,false)
                MenuItems.VACCINE -> inflater.inflate(R.layout.vaccine_fragment_layout,container,false)
                MenuItems.POSTS -> inflater.inflate(R.layout.posts_fragment_layout,container,false)
            }
        }

        return view
    }
}