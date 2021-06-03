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
        cd.add(
            covidRetrofit.getNumbersFromState(state.trim())
                .subscribeOn(
                    Schedulers.io()
                ).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    //if response is valid
                    if(response.data.isNotEmpty()) {

                        cityLiveData.postValue(response.data[0].region.cities)
                        regionLiveData.postValue(response.data[0])
                        cd.clear()
                    }
                    else{
                        Log.d("TAG_X", "RESPONSE INVALID")
                    }
                }, {
                    cd.clear()
                })
        )
    }

    override fun onCleared() {
        cd.clear()
        super.onCleared()
    }

}