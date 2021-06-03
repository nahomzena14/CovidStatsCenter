package com.example.covidstats.view.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covidstats.R
import com.example.covidstats.placesModel.Result
import kotlinx.android.synthetic.main.vaccination_item_layout.view.*

class PlacesAdapter: RecyclerView.Adapter<PlacesAdapter.LocationViewHolder>() {

    private  var placesList:List<Result> = listOf()
    inner class  LocationViewHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(
            R.layout.vaccination_item_layout,
            parent,
            false
        )
        return LocationViewHolder(item)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        placesList[position].let {
            holder.itemView.apply {
                vaccine_name_textview.text = it.name
                vaccine_address_textview.text = it.vicinity
                //if (it.opening_hours.open_now)
                   vaccine_open_close_textview.text = "Open Now"
                //else
                    //vaccine_open_close_textview.text = "Closed Now"
            }
        }
    }

    override fun getItemCount(): Int {
        return placesList.size
    }

    fun updateList(list: List<Result>){
        placesList = list
        notifyDataSetChanged()
    }

}