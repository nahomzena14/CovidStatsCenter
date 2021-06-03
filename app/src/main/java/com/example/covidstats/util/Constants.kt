package com.example.covidstats.util

import android.location.Location

class Constants {

    //https://covid-api.com/api/reports?region_province=Virginia
    companion object {
        const val BASE_URL: String = "https://covid-api.com"
        const val END_POINT: String = "api/reports"
        const val REGION: String = "region_province"
        const val CITY: String = "city_name"
        const val API_KEY:String = "AIzaSyCaD0C9xcGm78OlHFqJ3l3xWGxKc1f2Dj4"
        var currentAddress: String = ""
        var currentState: String = ""
        var currentCity: String = ""
        lateinit var location:Location
        var ready = false
        const val REQUEST_CODE = 777
    }
}