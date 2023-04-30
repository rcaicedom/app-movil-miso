package com.example.vinilos.ui.artistas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.artista.Artista
import com.example.vinilos.databinding.ActivityArtistaBinding
import com.example.vinilos.ui.album.AlbumesActivity
import com.example.vinilos.viewmodel.artistas.ArtistaViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class ArtistasActivity : AppCompatActivity() {

    private lateinit var artistaBinding: ActivityArtistaBinding
    private lateinit var viewModel: ArtistaViewModel
    private lateinit var adapter: ArtistasAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var navigation: BottomNavigationView
    private lateinit var artistasVacios: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artistaBinding = ActivityArtistaBinding.inflate(layoutInflater)
        setContentView(artistaBinding.root)
        recycler = findViewById(R.id.artistasRecycler)
        adapter = ArtistasAdapter()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        navigation = artistaBinding.artistasBottomNavigation
        navigation.selectedItemId = R.id.artistas
        artistasVacios = artistaBinding.textArtistasVacios

        viewModel = ViewModelProvider(this, ArtistaViewModel.Factory(application)).get(ArtistaViewModel::class.java)
        viewModel.artistas.observe(this) {
            if(it.isNotEmpty()){
                recycler.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<Artista>)
                artistasVacios.visibility = View.GONE
            } else {
                artistasVacios.visibility = View.VISIBLE
            }
        }
        viewModel.eventNetworkError.observe(this) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.artistas -> true
                R.id.albumes -> {
                    ActivityCompat.startActivity(
                        this,
                        Intent(this, AlbumesActivity::class.java), null
                    );
                    true
                }
                else -> true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navigation.selectedItemId = R.id.artistas
    }
    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
            artistasVacios.text = "Error de conexion"
            artistasVacios.visibility = View.VISIBLE
        }
    }
}