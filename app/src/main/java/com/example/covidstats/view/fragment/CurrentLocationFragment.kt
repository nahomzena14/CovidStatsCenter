package com.example.covidstats.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.covidstats.R
import com.example.covidstats.model.City
import com.example.covidstats.util.Constants
import com.example.covidstats.viewmodel.CovidViewModel
import com.example.covidstats.viewmodel.CovidViewModel2
import kotlinx.android.synthetic.main.current_location_fragment_layout.*

class CurrentLocationFragment : Fragment() {

    private val viewModel: CovidViewModel2 by activityViewModels()
    private var temp = ""
    private lateinit var topThreeCities: MutableList<City>

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
                Log.d("TAG_X",userCurrentState)
                //set views
                avoid_textview.text = "These counties in $userCurrentState are considered high risk"
                //city portion
                viewModel.cityLiveData.observe(viewLifecycleOwner, { city ->
                    topThreeCitiesGetter(city)

                    var length = topThreeCities.indices as Int
                    Log.d("TAG_X","ONE"+topThreeCities[0].name)
                    Log.d("TAG_X","TWO"+topThreeCities[1].name)
                    Log.d("TAG_X","THREE"+topThreeCities[2].name)

                    if(0 < length){
                        avoid_1.text = topThreeCities[0].name +": Active cases: ${topThreeCities[0].confirmed}"
                    }
                    if(1 < length){
                        avoid_2.text = topThreeCities[1].name +": Active cases: ${topThreeCities[1].confirmed}"
                    }
                    if(2 < length){
                        avoid_3.text = topThreeCities[2].name +": Active cases: ${topThreeCities[2].confirmed}"
                    }

                })
                viewModel.regionLiveData.observe(viewLifecycleOwner, { state ->
                    state_name_textview.text = state.region.province
                    state_confirmed_textview.text =
                        "Confirmed cases: " + state.confirmed.toString()
                    state_deaths_textview.text = "Death: " + state.deaths.toString()
                    state_active_textview.text = "Active cases: " + state.confirmed.toString()
                    fatality_rate_textview.text =
                        "Fatality rate: " + state.fatality_rate.toString()
                })

            }
        }
    }

    fun topThreeCitiesGetter(city: List<City>){

        val sortedList = city.sortedWith(compareBy { it.confirmed })

        for (i in city){
            topThreeCities.add(i)
        }
    }

}