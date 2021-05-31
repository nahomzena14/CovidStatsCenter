package com.example.covidstats.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.covidstats.R
import com.example.covidstats.databinding.ActivityMainBinding
import com.example.covidstats.model.City
import com.example.covidstats.model.Data
import com.example.covidstats.view.adapter.MainViewPagerAdapter
import com.example.covidstats.view.fragment.SearchFragment
import com.example.covidstats.viewmodel.CovidViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.search_fragment_layout.*

class MainActivity : AppCompatActivity() {

    private lateinit var pageAdapter: MainViewPagerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pageAdapter = MainViewPagerAdapter(supportFragmentManager)
        viewpager.adapter = pageAdapter

        main_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.current_location_menu -> openFragment(0)
                R.id.search_menu -> openFragment(1)
                R.id.vaccine_menu -> openFragment(2)
                R.id.posts_menu -> openFragment(3)
            }
            true
        }
    }

    private fun openFragment(frag: Int) {
        viewpager.setCurrentItem(frag, true)
    }
}