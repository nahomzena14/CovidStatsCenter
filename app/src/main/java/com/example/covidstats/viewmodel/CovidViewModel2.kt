package com.example.covidstats.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covidstats.covidModel.City
import com.example.covidstats.covidModel.Data
import com.example.covidstats.network.CovidRetrofit
import com.example.covidstats.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


//FOR CURRENT LOCATION TAB
class CovidViewModel2 : ViewModel() {

    private val cd = CompositeDisposable()

    private val covidRetrofit = CovidRetrofit()
    var cityLiveData = MutableLiveData<List<City>>()
    var regionLiveData = MutableLiveData<Data>()

    fun getNumbers(state: String) {
        Log.d("TAG_X", Constants.currentState)
        cd.add(
            covidRetrofit.getNumbersFromState(state.trim())
                .subscribeOn(
                    Schedulers.io()
                ).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if(response.data.isNotEmpty()) {
                        //Log.d("TAG_X", "City: " + response.data[0].region.cities[0])
                        //Log.d("TAG_X", "LENGTH: "+response.data.size.toString())

                        //0 = USA and get cities from there
                        //DATA(state stats) - REGION (province = state name)- region.cities[0] = city details
                        cityLiveData.postValue(response.data[0].region.cities)
                        Log.d("TAG_X", "RESPONSE = ${response.data[0].region.cities.size}")
                        regionLiveData.postValue(response.data[0])

                        //Log.d("TAG_X", cityResult.name)

                        cd.clear()
                    }
                    else{
                        Log.d("TAG_X", "RESPONSE INVALID")
                    }
                }, { throwable ->
                    cd.clear()
                    Log.d("TAG_X", throwable.toString())
                })
        )
    }

    override fun onCleared() {
        cd.clear()
        super.onCleared()
    }

}