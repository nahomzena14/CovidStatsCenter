package com.example.covidstats.util

class Constants {

    //https://covid-api.com/api/reports?region_province=Virginia
    companion object {
        const val BASE_URL: String = "https://covid-api.com"
        const val END_POINT: String = "api/reports"
        const val REGION: String = "region_province"
        const val REGION_VALUE: String = ""
        const val CITY: String = "city_name"
        const val CITY_VALUE: String = ""
        var currentAddress: String = ""
        var currentState: String = ""
        var currentCity: String = ""

        fun setAddress(add: String) {
            currentAddress = add
        }

        fun setCity(city: String) {
            currentCity = city
        }

        fun setState(state: String) {
            currentState = state
        }

    }
}