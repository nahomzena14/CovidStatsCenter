package com.example.covidstats.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.covidstats.R
import com.example.covidstats.databinding.ActivityMainBinding
import com.example.covidstats.util.Constants
import com.example.covidstats.util.MyLocationListener
import com.example.covidstats.view.adapter.MainViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var pageAdapter: MainViewPagerAdapter
    private lateinit var binding: ActivityMainBinding

    //location helpers
    private lateinit var locationManager: LocationManager
    lateinit var myLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (this@MainActivity::myLocation.isInitialized) {
            makeApiCall(myLocation)
        }

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

    private val myLocationListener = MyLocationListener(
        object : MyLocationListener.LocationDelegate {
            override fun provideLocation(location: Location) {
                myLocation = location
                makeApiCall(myLocation)
            }
        }
    )

    // .locality = state name
    private fun makeApiCall(myLocation: Location) {
        Geocoder(this, Locale.getDefault()).getFromLocation(
            myLocation.latitude,
            myLocation.longitude,
            1
        )
                //set address parts
            .also {
                Constants.currentCity = it[0].locality.toString()
                Constants.currentState =  it[0].adminArea
                Constants.currentAddress = it[0].getAddressLine(0)

                Log.d("TAG_X","City: "+Constants.currentCity)
                Log.d("TAG_X","State: "+Constants.currentState)
                Log.d("TAG_X","Addy: "+Constants.currentAddress)
            }
    }

    private fun openFragment(frag: Int) {
        viewpager.setCurrentItem(frag, true)
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000L,
            5f,
            myLocationListener
        )
    }

    @SuppressLint("MissingPermission")
    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(myLocationListener)
    }
}