package com.example.covidstats.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.covidstats.R
import com.example.covidstats.covidModel.City
import com.example.covidstats.util.Constants
import com.example.covidstats.viewmodel.CovidViewModel2
import kotlinx.android.synthetic.main.current_location_fragment_layout.*

class CurrentLocationFragment : Fragment() {

    private val viewModel: CovidViewModel2 by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_location_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        var userCurrentState = Constants.currentState.trim()
        var userCurrentAddress = Constants.currentAddress.trim()

        if (userCurrentAddress.isEmpty()) {
            get_location_button.isClickable = false
        } else {
            get_location_button.setOnClickListener {
                viewModel.getNumbers(userCurrentState)
                //set views
                "These counties in $userCurrentState are considered high risk".also {
                    avoid_textview.text = it
                }
                //city portion
                viewModel.cityLiveData.observe(viewLifecycleOwner, { city ->
                    Log.d("TAG_X", "GONNA ASSIGN TOP 3")
                    var tempCity = topThreeCitiesGetter(city)
                    Log.d("TAG_X", "ONE" + tempCity?.get(0)?.name)
                    Log.d("TAG_X", "TWO" + tempCity?.get(1)?.name)
                    Log.d("TAG_X", "THREE" + tempCity?.get(2)?.name)

                    var length = tempCity?.size

                    if (0 < length!!) {
                        (tempCity?.get(length - 1)?.name + ": Active cases: ${
                            tempCity?.get(length - 1)?.confirmed
                        }").also { avoid_1.text = it }
                    }
                    if (1 < length!!) {
                        (tempCity?.get(length - 2)?.name + ": Active cases: ${
                            tempCity?.get(length - 2)?.confirmed
                        }").also { avoid_2.text = it }
                    }
                    if (2 < length!!) {
                        (tempCity?.get(length - 3)?.name + ": Active cases: ${
                            tempCity?.get(length - 3)?.confirmed
                        }").also { avoid_3.text = it }
                    }
                    if (3 < length!!) {
                        (tempCity?.get(length - 4)?.name + ": Active cases: ${
                            tempCity?.get(length - 4)?.confirmed
                        }").also { avoid_4.text = it }
                    }
                    if (4 < length!!) {
                        (tempCity?.get(length - 5)?.name + ": Active cases: ${
                            tempCity?.get(length - 4)?.confirmed
                        }").also { avoid_5.text = it }
                    }

                })
                viewModel.regionLiveData.observe(viewLifecycleOwner, { state ->
                    state_name_textview.text = state.region.province
                    ("Confirmed cases: " + state.confirmed.toString()).also {
                        state_confirmed_textview.text = it
                    }
                    ("Death: " + state.deaths.toString()).also { state_deaths_textview.text = it }
                    ("Active cases: " + state.confirmed.toString()).also {
                        state_active_textview.text = it
                    }
                    ("Fatality rate: " + state.fatality_rate.toString()).also {
                        fatality_rate_textview.text = it
                    }
                })

            }
        }
    }

    private fun topThreeCitiesGetter(city: List<City>): List<City>? {

        return city.sortedWith(compareBy { it.confirmed })
    }

}