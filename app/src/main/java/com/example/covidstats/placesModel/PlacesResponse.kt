package com.example.covidstats.placesModel

data class PlacesResponse(
    val html_attributions: List<Any>,
    val next_page_token: String,
    val results: List<Result>,
    val status: String
)