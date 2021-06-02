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


    fun getNumbers(state: String, city: String) {

        cd.add(
            covidRetrofit.getNumbers(state, city)
                .subscribeOn(
                    Schedulers.io()
                ).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (response.data.size != 0) {
                        //0 = USA and get cities from there
                        //DATA(state stats) - REGION (province = state name)- region.cities[0] = city details
                        Log.d("TAG_X", "City: " + response.data[0].region.cities[0])
                        cityLiveData.postValue(response.data[0].region.cities[0])
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