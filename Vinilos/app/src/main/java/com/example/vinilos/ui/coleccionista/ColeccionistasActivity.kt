package com.example.vinilos.ui.coleccionista

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.coleccionista.Coleccionista
import com.example.vinilos.databinding.ActivityColeccionistasBinding
import com.example.vinilos.ui.album.AlbumesActivity
import com.example.vinilos.ui.artistas.ArtistasActivity
import com.example.vinilos.viewmodel.coleccionistas.ColeccionistaViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class ColeccionistasActivity: AppCompatActivity() {

    private lateinit var coleccionistasBinding: ActivityColeccionistasBinding
    private lateinit var viewModel: ColeccionistaViewModel
    private lateinit var adapter: ColeccionistasAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var navigation: BottomNavigationView
    private lateinit var coleccionistasVacios: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coleccionistasBinding = ActivityColeccionistasBinding.inflate(layoutInflater)
        setContentView(coleccionistasBinding.root)
        recycler = findViewById(R.id.coleccionistasRecycler)
        adapter = ColeccionistasAdapter(this)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        navigation = coleccionistasBinding.coleccionistasBottomNavigation
        navigation.selectedItemId = R.id.coleccionistas
        coleccionistasVacios = coleccionistasBinding.textColeccionistasVacios

        viewModel = ViewModelProvider(this, ColeccionistaViewModel.Factory(application)).get(
            ColeccionistaViewModel::class.java)
        viewModel.coleccionistas.observe(this) {
            if(it.isNotEmpty()){
                recycler.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<Coleccionista>)
                coleccionistasVacios.visibility = View.GONE
            } else {
                coleccionistasVacios.visibility = View.VISIBLE
            }
        }
        viewModel.eventNetworkError.observe(this) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.coleccionistas -> true
                R.id.albumes -> {
                    intent = Intent(this, AlbumesActivity::class.java)
                    intent.flags =Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    ActivityCompat.startActivity(
                        this,
                        intent,
                        null
                    );
                    true
                }
                R.id.artistas -> {
                    intent = Intent(this, ArtistasActivity::class.java)
                    intent.flags =Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    ActivityCompat.startActivity(
                        this,
                        intent,
                        null
                    );
                    true
                }
                else -> true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navigation.selectedItemId = R.id.coleccionistas
    }
    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Error de conexion", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
            coleccionistasVacios.text = "Error de conexion"
            coleccionistasVacios.visibility = View.VISIBLE
        }
    }
}