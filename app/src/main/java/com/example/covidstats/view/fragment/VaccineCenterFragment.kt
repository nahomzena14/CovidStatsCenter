package com.example.covidstats.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.covidstats.R
import com.example.covidstats.util.Constants
import com.example.covidstats.view.adapter.PlacesAdapter
import com.example.covidstats.viewmodel.PlacesViewModel
import kotlinx.android.synthetic.main.vaccine_fragment_layout.*

//fragment to show nearby vaccine centers
class VaccineCenterFragment:Fragment() {

    private val viewModel:PlacesViewModel by activityViewModels()
    private val adapter = PlacesAdapter()

    //inflate
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.vaccine_fragment_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vaccine_recyclerview.adapter = adapter
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(vaccine_recyclerview)
        super.onViewCreated(view, savedInstanceState)

        hospital_imageview.setOnClickListener(){
            //make api call
            viewModel.getPlacesNearMe(Constants.location)
            viewModel.liveData.observe(viewLifecycleOwner,{
                //observe live data from view model and update recycler view's list
                adapter.updateList(it)
            })
        }
    }
}