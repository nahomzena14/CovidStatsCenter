package com.example.covidstats.network

import com.example.covidstats.covidModel.CovidResponse
import com.example.covidstats.util.Constants.Companion.BASE_URL
import com.example.covidstats.util.Constants.Companion.CITY
import com.example.covidstats.util.Constants.Companion.END_POINT
import com.example.covidstats.util.Constants.Companion.REGION
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class CovidRetrofit {

    private val covidService = createRetrofit().create(CovidService::class.java)

    private fun createRetrofit():Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun getNumbers(state: String,city: String):Single<CovidResponse> {
        return covidService.getNumbers(state,city)
    }

    fun getNumbersFromState(state:String):Single<CovidResponse>{
        return covidService.getNumbersFromState(state)
    }

    interface CovidService {

        @GET(END_POINT)
        fun getNumbers(@Query(REGION) state: String,@Query(CITY) city: String): Single<CovidResponse>
        @GET(END_POINT)
        fun getNumbersFromState(@Query(REGION) state: String): Single<CovidResponse>

    }
}