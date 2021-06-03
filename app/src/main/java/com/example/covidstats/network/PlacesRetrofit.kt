package com.example.covidstats.network

import com.example.covidstats.placesModel.PlacesResponse
import com.example.covidstats.util.Constants.Companion.API_KEY
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class PlacesRetrofit {
    private val placeEndPoint = createRetrofit().create(PlaceEndPoint::class.java)

    private fun createRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    suspend fun makeApiCallAsync(
        type: String,
        keyWord: String,
        location: String,
        radius: Int
    ): Deferred<PlacesResponse> {
        return placeEndPoint.getPlaces(
            type,
            API_KEY,
            keyWord,
            location,
            radius
        )
    }

    interface PlaceEndPoint {
        @GET("/maps/api/place/nearbysearch/json")
        fun getPlaces(
            @Query("type") placeType: String,
            @Query("key") apiKey: String,
            @Query("keyword") keyWord: String,
            @Query("location") location: String,
            @Query("radius") radius: Int
        ): Deferred<PlacesResponse>

    }


}
