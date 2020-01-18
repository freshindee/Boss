package com.fitscorp.sl.apps.menu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fitscorp.sl.apps.R
import com.fitscorp.sl.apps.menu.data.LeaderboardMainData
import kotlinx.android.synthetic.main.leaderboard_cell.view.*



class LeaderboardAapter(val context: Context,val items : ArrayList<LeaderboardMainData>) : RecyclerView.Adapter<ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.leaderboard_cell, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvAnimalType?.text = items[position].average_Sales
        holder.txt_number?.text = items[position].position.toString()

    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val txt_number = view.txt_number
    val tvAnimalType = view.txt_name

}
