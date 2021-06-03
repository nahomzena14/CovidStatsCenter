package com.example.covidstats.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covidstats.covidModel.City
import com.example.covidstats.covidModel.Data
import com.example.covidstats.network.CovidRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CovidViewModel : ViewModel() {

    private val cd = CompositeDisposable()
    private var validInput:Boolean = false;
    private val covidRetrofit = CovidRetrofit()
    var cityLiveData = MutableLiveData<City>()
    var regionLiveData = MutableLiveData<Data>()


    fun getNumbers(state: String, city: String) {
        validInput = true
        cd.add(
            covidRetrofit.getNumbers(state, city)
                .subscribeOn(
                    Schedulers.io()
                ).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    if (response.data.isNotEmpty()) {
                        //0 = USA and get cities from there
                        //DATA(state stats) - REGION (province = state name)- region.cities[0] = city details
                        cityLiveData.postValue(response.data[0].region.cities[0])
                        regionLiveData.postValue(response.data[0])

                        //Log.d("TAG_X", cityResult.name)
                        cd.clear()
                    }
                    else{
                        validInput = false
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

    fun getInputValid():Boolean{
        return validInput;
    }

}