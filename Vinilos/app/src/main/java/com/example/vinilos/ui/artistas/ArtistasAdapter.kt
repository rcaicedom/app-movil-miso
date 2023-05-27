package com.example.vinilos.ui.artistas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.artista.Artista

class ArtistasAdapter(private val contexto: AppCompatActivity, private val esColeccionista: Boolean) :RecyclerView.Adapter<ArtistasAdapter.ArtistaViewHolder>() {
    private var data: ArrayList<Artista>? = null

    fun setData(list: ArrayList<Artista>) {
        data = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ArtistaViewHolder {
        return ArtistaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.artista_icon, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ArtistaViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item,contexto, esColeccionista)

    }

    class ArtistaViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(item: Artista?, contexto: AppCompatActivity, esColeccionista: Boolean) {
            val name: TextView = itemView.findViewById(R.id.artistaIconName)
            name.text = item?.name
            val boton: Button =itemView.findViewById(R.id.botonArtista)
            boton.setOnClickListener {
                val intent = Intent(contexto, ArtistaDetalleActivity::class.java)
                intent.putExtra("ID-ARTISTA", item?.id)
                intent.putExtra("ES-MUSICO", item?.birthDate != null)
                intent.putExtra("COLECCIONISTA", esColeccionista)
                ActivityCompat.startActivity(contexto, intent, null)
            }
        }
    }
}