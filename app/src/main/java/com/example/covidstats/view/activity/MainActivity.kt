package com.example.covidstats.view.activity

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.covidstats.R
import com.example.covidstats.databinding.ActivityMainBinding
import com.example.covidstats.util.Constants
import com.example.covidstats.util.Constants.Companion.REQUEST_CODE
import com.example.covidstats.util.MyLocationListener
import com.example.covidstats.view.adapter.MainViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
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

        //call geocode to get user's location
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (this@MainActivity::myLocation.isInitialized) {
            makeApiCall(myLocation)
        }

        pageAdapter = MainViewPagerAdapter(supportFragmentManager)
        viewpager.adapter = pageAdapter

        main_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.advice_menu -> openFragment(0)
                R.id.vaccine_menu -> openFragment(1)
                R.id.current_location_menu -> openFragment(2)
                R.id.search_menu -> openFragment(3)
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

    //get current user location using geocode
    private fun makeApiCall(myLocation: Location) {
        Geocoder(this, Locale.getDefault()).getFromLocation(
            myLocation.latitude,
            myLocation.longitude,
            1
        )
            //update address parts
            .also {
                Constants.ready = true
                Constants.currentCity = it[0].locality.toString()
                Constants.currentState = it[0].adminArea
                Constants.currentAddress = it[0].getAddressLine(0)
                Constants.location = myLocation
            }
    }

    private fun openFragment(frag: Int) {
        viewpager.setCurrentItem(frag, true)
    }

    override fun onStart() {
        super.onStart()

        //if location is not granted then request
        if (ContextCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission()
        }

        //if permission is given, request current location
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000L,
            5f,
            myLocationListener
        )
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(ACCESS_FINE_LOCATION), REQUEST_CODE
        )
    }

    override fun onStop() {
        super.onStop()
        locationManager.removeUpdates(myLocationListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE) {

            if (permissions[0] == ACCESS_FINE_LOCATION) {

                if (grantResults[0] != PackageManager.PERMISSION_GRANTED)

                    if (shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {
                        requestLocationPermission()
                    } else {
                        AlertDialog.Builder(
                            ContextThemeWrapper(
                                this,
                                R.style.ThemeOverlay_AppCompat
                            )
                        ).setTitle("Location Permission Needed")
                            .setMessage("Location is required to run this app. Allow request")
                            .setPositiveButton("Open Settings") { _, _ ->
                                //Implicit Intent
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                intent.data = Uri.fromParts("package", packageName, null)
                                startActivity(intent)
                            }.create().show()
                    }
            }
        }
    }
}