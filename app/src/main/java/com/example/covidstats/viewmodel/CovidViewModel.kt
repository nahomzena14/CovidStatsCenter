package com.example.covidstats.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covidstats.model.City
import com.example.covidstats.model.Data
import com.example.covidstats.network.CovidRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CovidViewModel : ViewModel() {

    private val cd = CompositeDisposable()

    private val covidRetrofit = CovidRetrofit()
    var cityLiveData = MutableLiveData<City>()
    var regionLiveData = MutableLiveData<Data>()

    private lateinit var cityResult: City
    private lateinit var stateResult: Data

    fun getNumbers(state: String, city: String) {

        cd.add(
            covidRetrofit.getNumbers(state, city)
                .subscribeOn(
                    Schedulers.io()
                ).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    //0 = USA and get cities from there
                    //DATA(state stats) - REGION (province = state name)- region.cities[0] = city details
                    cityLiveData.postValue(response.data[0].region.cities[0])
                    regionLiveData.postValue(response.data[0])

                    cityResult = response.data[0].region.cities[0]
                    stateResult = response.data[0]
                    cd.clear()

                }, { throwable ->
                    cd.clear()
                    Log.d("TAG_X", throwable.toString())
                })
        )
/*
        Log.d("TAG_X", getState()?.region?.province!!)*/

    }

    override fun onCleared() {
        cd.clear()
        super.onCleared()
    }
}