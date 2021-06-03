package com.example.covidstats.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covidstats.covidModel.City
import com.example.covidstats.covidModel.Data
import com.example.covidstats.network.CovidRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

//view model to make api call - for search tab
class CovidViewModel : ViewModel() {

    //use rx java to make api call
    private val cd = CompositeDisposable()
    private var validInput:Boolean = false
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

                    //if response is received
                    if (response.data.isNotEmpty()) {
                        cityLiveData.postValue(response.data[0].region.cities[0])
                        regionLiveData.postValue(response.data[0])
                        cd.clear()
                    }
                    //if api call fails
                    else{
                        validInput = false
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
        return validInput
    }

}