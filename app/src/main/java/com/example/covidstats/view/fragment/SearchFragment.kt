package com.example.covidstats.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.covidstats.R
import com.example.covidstats.viewmodel.CovidViewModel
import kotlinx.android.synthetic.main.search_fragment_layout.*

class SearchFragment() : Fragment() {

    private val viewModel = CovidViewModel()

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

            val cityName = city_edittext.text.toString().trim()
            val stateName = state_edittext.text.toString().trim()

            viewModel.getNumbers(stateName, cityName)

            //city
            viewModel.cityLiveData.observe(viewLifecycleOwner, { city ->
                city_name_textview.text = city.name
                date_textview.text = "Date: "+ city.date
                active_cases_textview.text = "Active Cases: "+city.confirmed.toString()
                deaths_textview.text = "Deaths: "+city.deaths.toString()
            })

            //state
            viewModel.regionLiveData.observe(viewLifecycleOwner, { state ->
                state_name_textview.text = state.region.province
                state_confirmed_textview.text = "Confirmed cases: "+state.confirmed.toString()
                state_deaths_textview.text = "Deaths: "+state.deaths.toString()
                state_active_textview.text = "Active cases: "+state.active.toString()
                fatality_rate_textview.text = "Fatality rate: "+state.fatality_rate.toString()
            })


        }
    }


}