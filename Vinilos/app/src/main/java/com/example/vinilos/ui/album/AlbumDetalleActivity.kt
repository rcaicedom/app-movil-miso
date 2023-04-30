package com.example.vinilos.ui.album

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.artista.Artista
import com.example.vinilos.data.track.Track
import com.example.vinilos.databinding.ActivityAlbumDetalleBinding
import com.example.vinilos.ui.artistas.ArtistasAdapter
import com.example.vinilos.ui.track.TracksAdapter
import com.example.vinilos.viewmodel.album.AlbumDetalleViewModel
import com.squareup.picasso.Picasso

class AlbumDetalleActivity: AppCompatActivity() {

    private lateinit var albumDetalleBinding: ActivityAlbumDetalleBinding
    private lateinit var viewModel: AlbumDetalleViewModel
    private var idAlbum: Int = 0
    private lateinit var albumTitle: TextView
    private lateinit var albumImagen: ImageView
    private lateinit var albumFecha: TextView
    private lateinit var albumDescripcion: TextView
    private lateinit var albumGenero: TextView
    private lateinit var albumCasa: TextView
    private lateinit var artistasAdapter: ArtistasAdapter
    private lateinit var artistasRecycler: RecyclerView
    private lateinit var artistasEmptyText: TextView
    private lateinit var tracksAdapter: TracksAdapter
    private lateinit var tracksRecycler: RecyclerView
    private lateinit var tracksEmptyText: TextView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        albumDetalleBinding = ActivityAlbumDetalleBinding.inflate(layoutInflater)
        setContentView(albumDetalleBinding.root)
        idAlbum = intent.getIntExtra("ID-ALBUM", 0)
        albumTitle = albumDetalleBinding.titleAlbumDetalle
        albumImagen = albumDetalleBinding.albumDetalleImagen
        albumFecha = albumDetalleBinding.textAlbumDetalleFecha
        albumDescripcion = albumDetalleBinding.textAlbumDetalleDescripcion
        albumGenero = albumDetalleBinding.textAlbumDetalleGenero
        albumCasa = albumDetalleBinding.textAlbumDetalleCasa

        artistasRecycler = albumDetalleBinding.albumArtistasRecycler
        artistasAdapter = ArtistasAdapter()
        artistasRecycler.layoutManager = LinearLayoutManager(this)
        artistasRecycler.adapter = artistasAdapter
        artistasEmptyText = albumDetalleBinding.artistasEmptyText

        tracksRecycler = albumDetalleBinding.albumTracksRecycler
        tracksAdapter = TracksAdapter()
        tracksRecycler.layoutManager = LinearLayoutManager(this)
        tracksRecycler.adapter = tracksAdapter
        tracksEmptyText = albumDetalleBinding.tracksEmptyText

        viewModel = ViewModelProvider(this, AlbumDetalleViewModel.Factory(application, idAlbum)).get(AlbumDetalleViewModel::class.java)
        viewModel.album.observe(this) {
            if(it!=null){
                albumTitle.text = it.name
                Picasso.get().load(it.cover).error(R.drawable.vinyl).into(albumImagen)
                albumFecha.text = it.releaseDate.split("T")[0]
                albumDescripcion.text = it.description
                albumGenero.text = it.genre
                albumCasa.text = it.recordLabel

                if(it.performers.isNotEmpty()) {
                    artistasEmptyText.visibility = View.GONE
                    artistasRecycler.visibility = View.VISIBLE
                    artistasAdapter.setData(it.performers as ArrayList<Artista>)
                } else {
                    artistasRecycler.visibility = View.GONE
                    artistasEmptyText.visibility = View.VISIBLE
                }

                if(it.tracks.isNotEmpty()) {
                    tracksEmptyText.visibility = View.GONE
                    tracksRecycler.visibility = View.VISIBLE
                    tracksAdapter.setData(it.tracks as ArrayList<Track>)
                } else {
                    tracksRecycler.visibility = View.GONE
                    tracksEmptyText.visibility = View.VISIBLE
                }
            }
            else {
                albumTitle.text = idAlbum.toString()
            }
        }
        viewModel.eventNetworkError.observe(this) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}