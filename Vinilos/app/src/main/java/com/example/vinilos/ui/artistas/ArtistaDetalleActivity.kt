package com.example.vinilos.ui.artistas

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.album.Album
import com.example.vinilos.data.album.AlbumColeccionista
import com.example.vinilos.data.artista.Artista
import com.example.vinilos.data.premio.Premio
import com.example.vinilos.databinding.ActivityArtistaDetalleBinding
import com.example.vinilos.ui.album.AlbumesAdapter
import com.example.vinilos.ui.album.AlbumesColeccionistaAdapter
import com.example.vinilos.ui.premio.PremiosAdapter
import com.example.vinilos.viewmodel.artistas.ArtistaDetalleViewModel
import com.squareup.picasso.Picasso

class ArtistaDetalleActivity: AppCompatActivity() {

    private lateinit var artistaDetalleBinding: ActivityArtistaDetalleBinding
    private lateinit var viewModel: ArtistaDetalleViewModel
    private var idArtista: Int = 0
    private var esMusico: Boolean = false
    private lateinit var artistaName: TextView
    private lateinit var artistaDate: TextView
    private lateinit var artistaDateLabel: TextView
    private lateinit var artistaDescripcion: TextView
    private lateinit var artistaImagen: ImageView

    private lateinit var albumesAdapter: AlbumesAdapter
    private lateinit var albumesRecycler: RecyclerView
    private lateinit var albumesEmptyText: TextView

    private lateinit var artistasAdapter: ArtistasAdapter
    private lateinit var artistasRecycler: RecyclerView
    private lateinit var artistasEmptyText: TextView
    private lateinit var artistasLayout: LinearLayout
    private lateinit var artistasLabel: TextView

    private lateinit var premiosAdapter: PremiosAdapter
    private lateinit var premiosRecycler: RecyclerView
    private lateinit var premiosEmptyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artistaDetalleBinding = ActivityArtistaDetalleBinding.inflate(layoutInflater)
        setContentView(artistaDetalleBinding.root)
        idArtista = intent.getIntExtra("ID-ARTISTA", 0)
        esMusico = intent.getBooleanExtra("ES-MUSICO", false)
        artistaName = artistaDetalleBinding.titleArtistaDetalle
        artistaDate = artistaDetalleBinding.textArtistaDetalleFecha
        artistaDateLabel = artistaDetalleBinding.labelArtistaDetalleFecha
        artistaDescripcion = artistaDetalleBinding.textArtistaDetalleDescripcion
        artistaImagen = artistaDetalleBinding.artistaDetalleImagen

        albumesRecycler = artistaDetalleBinding.artistaAlbumesRecycler
        albumesAdapter = AlbumesAdapter(this)
        albumesRecycler.layoutManager = GridLayoutManager(this, 2)
        albumesRecycler.adapter = albumesAdapter
        albumesEmptyText = artistaDetalleBinding.albumesEmptyText

        artistasRecycler = artistaDetalleBinding.artistaArtistasRecycler
        artistasAdapter = ArtistasAdapter(this)
        artistasRecycler.layoutManager = LinearLayoutManager(this)
        artistasRecycler.adapter = artistasAdapter
        artistasEmptyText = artistaDetalleBinding.artistasEmptyText
        artistasLayout = artistaDetalleBinding.artistaArtistasLayout
        artistasLabel = artistaDetalleBinding.labelArtistaDetalleArtistas

        premiosRecycler = artistaDetalleBinding.artistaPremiosRecycler
        premiosAdapter = PremiosAdapter(this)
        premiosRecycler.layoutManager = LinearLayoutManager(this)
        premiosRecycler.adapter = premiosAdapter
        premiosEmptyText = artistaDetalleBinding.premiosEmptyText

        viewModel = ViewModelProvider(this, ArtistaDetalleViewModel.Factory(application, idArtista, esMusico)).get(
            ArtistaDetalleViewModel::class.java)
        viewModel.artista.observe(this) {
            if(it!=null){
                artistaName.text = it.name
                artistaDescripcion.text = it.description
                Picasso.get().load(it.image).into(artistaImagen)
                if(esMusico){
                    artistaDateLabel.text = "Fecha de Nacimiento:"
                    artistaDate.text = it.birthDate?.split("T")?.get(0)
                }else{
                    artistaDateLabel.text = "Fecha de Creacion:"
                    artistaDate.text = it.creationDate?.split("T")?.get(0)
                }

                if(it.albums.isNotEmpty()) {
                    albumesEmptyText.visibility = View.GONE
                    albumesRecycler.visibility = View.VISIBLE
                    albumesAdapter.setData(it.albums as ArrayList<Album>)
                } else {
                    albumesRecycler.visibility = View.GONE
                    albumesEmptyText.visibility = View.VISIBLE
                }

                if(!esMusico) {
                    if (it.musicians?.isNotEmpty() == true) {
                        artistasEmptyText.visibility = View.GONE
                        artistasRecycler.visibility = View.VISIBLE
                        artistasAdapter.setData(it.musicians as ArrayList<Artista>)
                    } else {
                        artistasRecycler.visibility = View.GONE
                        artistasEmptyText.visibility = View.VISIBLE
                    }
                } else {
                    artistasRecycler.visibility = View.GONE
                    artistasEmptyText.visibility = View.GONE
                    artistasLayout.visibility = View.GONE
                    artistasLabel.visibility = View.GONE
                }

                if(it.performerPrizes.isNotEmpty()) {
                    premiosEmptyText.visibility = View.GONE
                    premiosRecycler.visibility = View.VISIBLE
                    premiosAdapter.setData(it.performerPrizes as ArrayList<Premio>)
                } else {
                    premiosRecycler.visibility = View.GONE
                    premiosEmptyText.visibility = View.VISIBLE
                }
            }
            else {
                artistaName.text = idArtista.toString()
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