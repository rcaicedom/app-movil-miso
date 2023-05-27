package com.example.vinilos.ui.album

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.album.AlbumColeccionista
import com.example.vinilos.viewmodel.album.AlbumDetalleViewModel
import com.squareup.picasso.Picasso


class AlbumesColeccionistaAdapter(private val contexto: AppCompatActivity) :
    RecyclerView.Adapter<AlbumesColeccionistaAdapter.AlbumesColeccionistaViewHolder>() {
    private var data: ArrayList<AlbumColeccionista>? = null

    fun setData(list: ArrayList<AlbumColeccionista>) {
        data = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): AlbumesColeccionistaViewHolder {
        return AlbumesColeccionistaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.album_icon, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: AlbumesColeccionistaViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item, contexto)

    }

    class AlbumesColeccionistaViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(item: AlbumColeccionista?, contexto: AppCompatActivity) {
            val id = item!!.id

            val viewModel = ViewModelProvider(
                contexto,
                AlbumDetalleViewModel.Factory(contexto.application, id)
            ).get(AlbumDetalleViewModel::class.java)
            viewModel.album.observe(contexto) { albumDetalle ->
                val name: TextView = itemView.findViewById(R.id.albumIconName)
                name.text = albumDetalle?.name

                val image: ImageView = itemView.findViewById(R.id.albumIconImage)
                Picasso.get().load(albumDetalle?.cover).error(R.drawable.vinyl).into(image)

                val layout: LinearLayout = itemView.findViewById(R.id.albumIconLayout)
                layout.setOnClickListener {
                    val intent = Intent(contexto, AlbumDetalleActivity::class.java)
                    intent.putExtra("ID-ALBUM", albumDetalle?.id)
                    ActivityCompat.startActivity(contexto, intent, null)
                }
            }
        }
    }
}