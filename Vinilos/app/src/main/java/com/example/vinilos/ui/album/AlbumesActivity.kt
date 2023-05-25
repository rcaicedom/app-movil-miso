package com.example.vinilos.ui.album

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.album.Album
import com.example.vinilos.databinding.ActivityAlbumesBinding
import com.example.vinilos.ui.artistas.ArtistasActivity
import com.example.vinilos.ui.coleccionista.ColeccionistasActivity
import com.example.vinilos.viewmodel.album.AlbumViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class AlbumesActivity : AppCompatActivity() {

    private lateinit var albumesBinding: ActivityAlbumesBinding
    private lateinit var viewModel: AlbumViewModel
    private lateinit var adapter: AlbumesAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var anadirButton: ImageButton
    private var esColeccionista: Boolean = false
    private lateinit var navigation: BottomNavigationView
    private lateinit var albumesVacios: TextView
    val CREATE_ALBUM_REQUEST = 74

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        albumesBinding = ActivityAlbumesBinding.inflate(layoutInflater)
        setContentView(albumesBinding.root)
        recycler = findViewById(R.id.recyclerAlbumes)
        adapter = AlbumesAdapter(this)
        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.adapter = adapter
        esColeccionista = intent.getBooleanExtra("COLECCIONISTA", false)
        anadirButton = albumesBinding.agregarAlbumButton
        if (!esColeccionista) {
            anadirButton.visibility = View.GONE
            anadirButton.isEnabled = false
        } else {
            anadirButton.setOnClickListener{
                try {
                    ActivityCompat.startActivityForResult(this, Intent(this, AlbumCreateActivity::class.java), CREATE_ALBUM_REQUEST, null)
                } catch (e: ActivityNotFoundException) {
                    // display error state to the user
                }
            }
        }
        loadData()
        albumesVacios = albumesBinding.textAlbumesVacios
        navigation = albumesBinding.albumesBottomNavigation
        navigation.selectedItemId = R.id.albumes

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.artistas -> {
                    intent = Intent(this, ArtistasActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    ActivityCompat.startActivity(
                        this,
                        intent,
                        null
                    )
                    true
                }

                R.id.coleccionistas -> {
                    intent = Intent(this, ColeccionistasActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    ActivityCompat.startActivity(
                        this,
                        intent,
                        null
                    )
                    true
                }

                R.id.albumes -> true

                else -> true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navigation.selectedItemId = R.id.albumes
    }

    private fun loadData(){
        viewModel = ViewModelProvider(
            this,
            AlbumViewModel.Factory(application)
        ).get(AlbumViewModel::class.java)
        viewModel.albums.observe(this) {
            if(it?.isNotEmpty() == true) {
                recycler.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<Album>)
                albumesVacios.visibility = View.GONE
            } else {
                albumesVacios.visibility = View.VISIBLE
            }
        }
        viewModel.eventNetworkError.observe(this) { isNetworkError ->
            if (isNetworkError) onNetworkError()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_ALBUM_REQUEST && resultCode == RESULT_OK) {
            val newAlbum = data?.getSerializableExtra("NEW-ALBUM")
            adapter.setData((adapter.dataAdapter?.plus(newAlbum)) as ArrayList<Album>)
        }
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
            albumesVacios.text = "Error de conexion"
            albumesVacios.visibility = View.VISIBLE
        }
    }
}