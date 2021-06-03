package com.example.covidstats.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.covidstats.R

//Fragment for showing image from CDC
class AdviceFragment:Fragment() {

    //inflate view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.advice_fragment_layout,container,false)
    }
}