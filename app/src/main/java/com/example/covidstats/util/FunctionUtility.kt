package com.example.covidstats.util

import android.location.Location

//Class to get longitude and latitude from user's current location
class FunctionUtility {
    companion object {
        fun Location.toFormattedString(): String =
            "${this.latitude},${this.longitude}"
    }
}