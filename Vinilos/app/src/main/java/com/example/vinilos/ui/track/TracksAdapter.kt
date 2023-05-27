package com.example.vinilos.ui.track

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.track.Track

class TracksAdapter() : RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {
    var dataAdapter: ArrayList<Track>? = null

    fun setData(list: ArrayList<Track>) {
        dataAdapter = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TracksViewHolder {
        return TracksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.track_icon, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataAdapter?.size ?: 0
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val item = dataAdapter?.get(position)
        holder.bindView(item)

    }

    class TracksViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(item: Track?) {
            val name: TextView = itemView.findViewById(R.id.trackIconName)
            name.text = item?.name
        }
    }
}