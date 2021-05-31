package com.example.covidstats.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import com.example.covidstats.R
import com.example.covidstats.view.adapter.MainViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var pageAdapter: MainViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pageAdapter = MainViewPagerAdapter(supportFragmentManager)
        viewpager.adapter = pageAdapter

        main_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.current_location_menu -> viewpager.setCurrentItem(0, true)
                R.id.search_menu -> viewpager.setCurrentItem(1, true)
                R.id.vaccine_menu -> viewpager.setCurrentItem(2, true)
                R.id.posts_menu -> viewpager.setCurrentItem(3, true)
            }
            true
        }

    }

    private fun openFragment(frag: Int) {
        viewpager.setCurrentItem(frag, true)
    }
}