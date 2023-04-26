package com.example.vinilos.ui.album

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.data.album.Album
import com.example.vinilos.databinding.ActivityAlbumesBinding
import com.example.vinilos.viewmodel.album.AlbumViewModel

class AlbumesActivity: AppCompatActivity() {

    private lateinit var albumesBinding: ActivityAlbumesBinding
    private lateinit var viewModel: AlbumViewModel
    private lateinit var adapter: AlbumesAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var albumesVaciosText : TextView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        albumesBinding = ActivityAlbumesBinding.inflate(layoutInflater)
        setContentView(albumesBinding.root)
        albumesVaciosText = albumesBinding.albumesVacioText
        albumesVaciosText.visibility = View.GONE
        recycler = findViewById(R.id.recyclerAlbumes)
        adapter = AlbumesAdapter()
        recycler.layoutManager = GridLayoutManager(this,2)
        recycler.adapter = adapter

        viewModel = ViewModelProvider(this, AlbumViewModel.Factory(application)).get(AlbumViewModel::class.java)
        viewModel.albums.observe(this) {
            if(it.isNotEmpty()) {
                albumesVaciosText.visibility = View.GONE
                recycler.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<Album>)
            }else{
                albumesVaciosText.visibility = View.VISIBLE
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