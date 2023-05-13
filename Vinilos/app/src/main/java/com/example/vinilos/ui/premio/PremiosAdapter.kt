package com.example.vinilos.ui.premio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.premio.Premio
import com.example.vinilos.viewmodel.premio.PremioViewModel

class PremiosAdapter(private val contexto: AppCompatActivity): RecyclerView.Adapter<PremiosAdapter.PremiosViewHolder>() {
    private var data: ArrayList<Premio>? = null

    fun setData(list: ArrayList<Premio>) {
        data = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PremiosViewHolder {
        return PremiosViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.premio_icon, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: PremiosViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item, contexto)

    }

    class PremiosViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(item: Premio?, contexto: AppCompatActivity) {
            val id = item!!.id

            val viewModel = ViewModelProvider(contexto, PremioViewModel.Factory(contexto.application, id)).get(
                PremioViewModel::class.java)
            viewModel.premio.observe(contexto){
                val name: TextView = itemView.findViewById(R.id.premioIconName)
                name.text = it?.name
            }
        }
    }
}