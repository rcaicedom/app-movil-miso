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
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.album.Album
import com.squareup.picasso.Picasso

class AlbumesAdapter(private val contexto: AppCompatActivity) :
    RecyclerView.Adapter<AlbumesAdapter.AlbumesViewHolder>() {
    private var data: ArrayList<Album>? = null

    fun setData(list: ArrayList<Album>) {
        data = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): AlbumesViewHolder {
        return AlbumesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.album_icon, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: AlbumesViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item, contexto)

    }

    class AlbumesViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(item: Album?, contexto: AppCompatActivity) {
            val name: TextView = itemView.findViewById(R.id.albumIconName)
            name.text = item?.name

            val image: ImageView = itemView.findViewById(R.id.albumIconImage)
            Picasso.get().load(item?.cover).error(R.drawable.vinyl).into(image)

            val layout: LinearLayout =itemView.findViewById(R.id.albumIconLayout)
            layout.setOnClickListener {
                val intent = Intent(contexto, AlbumDetalleActivity::class.java)
                intent.putExtra("ID-ALBUM", item?.id)
                ActivityCompat.startActivity(contexto, intent, null)
            }
        }
    }
}