package com.example.covidstats.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.covidstats.R
import com.example.covidstats.covidModel.City
import com.example.covidstats.util.Constants
import com.example.covidstats.viewmodel.CovidViewModel2
import kotlinx.android.synthetic.main.current_location_fragment_layout.*

//Fragment to update current location tab
class CurrentLocationFragment : Fragment() {

    private val viewModel: CovidViewModel2 by activityViewModels()

    //inflate
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_location_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        //check if main activity has updated user's current location
        var userCurrentState = Constants.currentState.trim()
        var userCurrentAddress = Constants.currentAddress.trim()

        get_location_button.setOnClickListener {
            //if address is not updated, check later
            if (Constants.currentAddress.isEmpty()) {
                get_location_button.isClickable = true
                Toast.makeText(activity,"Getting your current location. Check other tabs for now",Toast.LENGTH_SHORT).show()
            }
            //when address is updated, let the user know and make a call
            else {
                //make api call using state name
                viewModel.getNumbers(userCurrentState)
                //set views
                //city portion - observe from livedata
                viewModel.cityLiveData.observe(viewLifecycleOwner, { city ->
                    Log.d("TAG_X", "GONNA ASSIGN TOP 3")
                    var tempCity = topThreeCitiesGetter(city)
                    var length = tempCity.size

                    if (0 < length) {
                        (tempCity[length - 1].name + ": Active cases: ${
                            tempCity[length - 1].confirmed
                        }").also { avoid_1.text = it }
                    }
                    if (1 < length) {
                        (tempCity[length - 2].name + ": Active cases: ${
                            tempCity[length - 2].confirmed
                        }").also { avoid_2.text = it }
                    }
                    if (2 < length) {
                        (tempCity[length - 3].name + ": Active cases: ${
                            tempCity[length - 3].confirmed
                        }").also { avoid_3.text = it }
                    }
                    if (3 < length) {
                        (tempCity[length - 4].name + ": Active cases: ${
                            tempCity[length - 4].confirmed
                        }").also { avoid_4.text = it }
                    }
                    if (4 < length) {
                        (tempCity[length - 5].name + ": Active cases: ${
                            tempCity[length - 4].confirmed
                        }").also { avoid_5.text = it }
                    }

                })
                //state portion
                viewModel.regionLiveData.observe(viewLifecycleOwner, { state ->
                    state_name_textview.text = state.region.province
                    ("Confirmed cases: " + state.confirmed.toString()).also {
                        state_confirmed_textview.text = it
                    }
                    ("Death: " + state.deaths.toString()).also { state_deaths_textview.text = it }
                    ("Active cases: " + state.active.toString()).also {
                        state_active_textview.text = it
                    }
                    ("Fatality rate: " + state.fatality_rate.toString()).also {
                        fatality_rate_textview.text = it
                    }
                })
                "These counties in $userCurrentState are considered high risk".also {
                    avoid_textview.text = it
                }
            }
        }
    }


    //sort list based on current confirmed cases
    private fun topThreeCitiesGetter(city: List<City>): List<City> {
        return city.sortedWith(compareBy { it.confirmed })
    }

}