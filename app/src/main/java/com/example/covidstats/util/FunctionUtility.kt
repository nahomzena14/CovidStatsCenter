package com.example.covidstats.util

import android.location.Location

class FunctionUtility {
    companion object {
        fun Location.toFormattedString(): String =
            "${this.latitude},${this.longitude}"
    }
}