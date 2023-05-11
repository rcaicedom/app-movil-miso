package com.example.vinilos.ui.coleccionista

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.coleccionista.Coleccionista
import com.example.vinilos.ui.album.AlbumDetalleActivity

class ColeccionistasAdapter(private val contexto: ColeccionistasActivity): RecyclerView.Adapter<ColeccionistasAdapter.ColeccionistaViewHolder>() {
    private var data: ArrayList<Coleccionista>? = null

    fun setData(list: ArrayList<Coleccionista>) {
        data = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ColeccionistaViewHolder {
        return ColeccionistaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.coleccionista_icon, parent, false), contexto
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ColeccionistaViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)

    }

    class ColeccionistaViewHolder(itemView: View, private val contexto: ColeccionistasActivity) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(item: Coleccionista?) {
            val name: TextView = itemView.findViewById(R.id.coleccionistaIconName)
            name.text = item?.name

            val boton: Button = itemView.findViewById(R.id.botonColeccionista)
            boton.setOnClickListener {
                val intent = Intent(contexto, ColeccionistaDetalleActivity::class.java)
                intent.putExtra("ID-COLECCIONISTA", item?.id)
                ActivityCompat.startActivity(contexto, intent, null)
            }
        }
    }
}