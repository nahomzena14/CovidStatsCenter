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
import com.example.covidstats.viewmodel.CovidViewModel
import com.example.covidstats.viewmodel.CovidViewModel2
import kotlinx.android.synthetic.main.search_fragment_layout.*

//fragment to let user search a specific county in USA
class SearchFragment() : Fragment() {

    private val viewModel: CovidViewModel by activityViewModels()

    //inflate
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_button.setOnClickListener() {

            //get user input
            val cityName = city_edittext.text.toString().trim()
            val stateName = state_edittext.text.toString().trim()

            //if input is empty
            if (cityName.isEmpty()) {
                toastMessage("Please enter a city name")
            } else if (stateName.isEmpty()) {
                toastMessage("Please enter a state name")
            }
            //if input is given
            else {
                //make api call
                viewModel.getNumbers(stateName, cityName)
                //city portion
                if (viewModel.getInputValid()) {
                    viewModel.cityLiveData.observe(viewLifecycleOwner, { city ->
                        city_name_textview.text = city.name
                        ("Date: " + city.date).also { date_textview.text = it }
                        ("Active Cases: " + city.confirmed.toString()).also { active_cases_textview.text = it }
                        ("Deaths: " + city.deaths.toString()).also { deaths_textview.text = it }
                    })
                    //state portion
                    viewModel.regionLiveData.observe(viewLifecycleOwner, { state ->
                        state_name_textview.text = state.region.province
                        ("Confirmed cases: " + state.confirmed.toString()).also { state_confirmed_textview.text = it }
                        ("Deaths: " + state.deaths.toString()).also { state_deaths_textview.text = it }
                        ("Active cases: " + state.active.toString()).also { state_active_textview.text = it }
                        ("Fatality rate: " + state.fatality_rate.toString()).also { fatality_rate_textview.text = it }
                    })
                }
                //Invalid request - county or state name is incorrect
                else {
                    toastMessage("CHECK COUNTY OR STATE NAME INPUT")
                }
            }
        }
    }

    //show error message to user using toast
    private fun toastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }
}