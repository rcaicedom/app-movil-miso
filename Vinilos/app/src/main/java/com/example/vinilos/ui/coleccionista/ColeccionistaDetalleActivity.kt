package com.example.vinilos.ui.coleccionista

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.data.album.AlbumColeccionista
import com.example.vinilos.data.artista.Artista
import com.example.vinilos.databinding.ActivityColeccionistaDetalleBinding
import com.example.vinilos.ui.album.AlbumesColeccionistaAdapter
import com.example.vinilos.ui.artistas.ArtistasAdapter
import com.example.vinilos.viewmodel.coleccionistas.ColeccionistaDetalleViewModel

class ColeccionistaDetalleActivity: AppCompatActivity() {

    private lateinit var coleccionistaDetalleBinding: ActivityColeccionistaDetalleBinding
    private lateinit var viewModel: ColeccionistaDetalleViewModel
    private var idColeccionista: Int = 0
    private lateinit var coleccionistaName: TextView
    private lateinit var artistasAdapter: ArtistasAdapter
    private lateinit var artistasRecycler: RecyclerView
    private lateinit var artistasEmptyText: TextView
    private lateinit var albumesColeccionistaAdapter: AlbumesColeccionistaAdapter
    private lateinit var albumesColeccionistaRecycler: RecyclerView
    private lateinit var albumesColeccionistaEmptyText: TextView
    private var esColeccionista: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coleccionistaDetalleBinding = ActivityColeccionistaDetalleBinding.inflate(layoutInflater)
        setContentView(coleccionistaDetalleBinding.root)
        idColeccionista = intent.getIntExtra("ID-COLECCIONISTA", 0)
        esColeccionista = intent.getBooleanExtra("COLECCIONISTA", false)
        coleccionistaName = coleccionistaDetalleBinding.titleColeccionistaDetalle

        artistasRecycler = coleccionistaDetalleBinding.coleccionistaArtistasRecycler
        artistasAdapter = ArtistasAdapter(this, esColeccionista)
        artistasRecycler.layoutManager = LinearLayoutManager(this)
        artistasRecycler.adapter = artistasAdapter
        artistasEmptyText = coleccionistaDetalleBinding.artistasEmptyTextColeccionista

        albumesColeccionistaRecycler = coleccionistaDetalleBinding.coleccionistaAlbumesRecycler
        albumesColeccionistaAdapter = AlbumesColeccionistaAdapter(this, esColeccionista)
        albumesColeccionistaRecycler.layoutManager = GridLayoutManager(this, 2)
        albumesColeccionistaRecycler.adapter = albumesColeccionistaAdapter
        albumesColeccionistaEmptyText = coleccionistaDetalleBinding.albumesEmptyTextColeccionista

        viewModel = ViewModelProvider(this, ColeccionistaDetalleViewModel.Factory(application, idColeccionista)).get(
            ColeccionistaDetalleViewModel::class.java)
        viewModel.coleccionista.observe(this) {
            if(it!=null){
                coleccionistaName.text = it.name

                if(it.favoritePerformers.isNotEmpty()) {
                    artistasEmptyText.visibility = View.GONE
                    artistasRecycler.visibility = View.VISIBLE
                    artistasAdapter.setData(it.favoritePerformers as ArrayList<Artista>)
                } else {
                    artistasRecycler.visibility = View.GONE
                    artistasEmptyText.visibility = View.VISIBLE
                }

                if(it.collectorAlbums.isNotEmpty()) {
                    albumesColeccionistaEmptyText.visibility = View.GONE
                    albumesColeccionistaRecycler.visibility = View.VISIBLE
                    albumesColeccionistaAdapter.setData(it.collectorAlbums as ArrayList<AlbumColeccionista>)
                } else {
                    albumesColeccionistaRecycler.visibility = View.GONE
                    albumesColeccionistaEmptyText.visibility = View.VISIBLE
                }
            }
            else {
                coleccionistaName.text = idColeccionista.toString()
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