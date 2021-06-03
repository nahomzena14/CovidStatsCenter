package com.example.covidstats.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.covidstats.R
import com.example.covidstats.util.Constants
import com.example.covidstats.view.adapter.PlacesAdapter
import com.example.covidstats.viewmodel.PlacesViewModel
import kotlinx.android.synthetic.main.vaccine_fragment_layout.*

class VaccineCenterFragment:Fragment() {

    private val viewModel:PlacesViewModel by activityViewModels()
    private val adapter = PlacesAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.vaccine_fragment_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vaccine_recyclerview.adapter = adapter
        super.onViewCreated(view, savedInstanceState)

        hospital_imageview.setOnClickListener(){
            Log.d("TAG_X","Button clicked")
            viewModel.getPlacesNearMe(Constants.location)
            viewModel.liveData.observe(viewLifecycleOwner,{
                Log.d("TAG_X","Updating result list")
                Log.d("TAG_X","NAME = ${it[0].name}")
                adapter.updateList(it)
            })
        }
    }
}