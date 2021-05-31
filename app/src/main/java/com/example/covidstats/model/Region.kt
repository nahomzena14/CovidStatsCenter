package com.example.covidstats.model

data class Region(
    val cities: List<City>,
    val iso: String,
    val lat: String,
    val long: String,
    val name: String,
    val province: String
)