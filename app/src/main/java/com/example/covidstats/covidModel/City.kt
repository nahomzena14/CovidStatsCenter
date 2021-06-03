package com.example.covidstats.covidModel

data class City(
    val confirmed: Int,
    val confirmed_diff: Int,
    val date: String,
    val deaths: Int,
    val deaths_diff: Int,
    val fips: Int,
    val last_update: String,
    val lat: String,
    val long: String,
    val name: String
)