package com.example.covidstats.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.covidstats.network.PlacesRetrofit
import com.example.covidstats.placesModel.Result
import com.example.covidstats.util.Constants
import com.example.covidstats.util.FunctionUtility.Companion.toFormattedString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlacesViewModel:ViewModel() {

    private var netJob: Job? = null
    private val retrofit = PlacesRetrofit()
    val liveData:MutableLiveData<List<Result>> = MutableLiveData()

    fun getPlacesNearMe(location: Location){

        Log.d("TAG_X","Location = $location")

        netJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = retrofit.makeApiCallAsync(
                    "hospital",
                    "vaccine",
                    location.toFormattedString(),
                    10000
                ).await()

                liveData.postValue(result.results)
            } catch (e: Exception) {
                Log.d("TAG_X","Error")
            }

        }
    }

    override fun onCleared() {
        netJob?.cancel()
        super.onCleared()
    }
}