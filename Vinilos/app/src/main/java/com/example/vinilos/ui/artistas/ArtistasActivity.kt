package com.example.vinilos.ui.artistas

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.artista.Artista
import com.example.vinilos.databinding.ActivityArtistaBinding
import com.example.vinilos.viewmodel.artistas.ArtistaViewModel


class ArtistasActivity : AppCompatActivity() {

    private lateinit var artistaBinding: ActivityArtistaBinding
    private lateinit var viewModel: ArtistaViewModel
    private lateinit var adapter: ArtistasAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        artistaBinding = ActivityArtistaBinding.inflate(layoutInflater)
        setContentView(artistaBinding.root)
        recycler = findViewById(R.id.artistasRecycler)
        adapter = ArtistasAdapter()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        viewModel = ViewModelProvider(this, ArtistaViewModel.Factory(application)).get(ArtistaViewModel::class.java)
        viewModel.artistas.observe(this) {
            if(it.isNotEmpty()){
                recycler.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<Artista>)
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