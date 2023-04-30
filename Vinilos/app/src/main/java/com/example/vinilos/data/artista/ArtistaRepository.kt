package com.example.vinilos.data.artista

import android.app.Application
import com.example.vinilos.network.NetworkServiceAdapter

class ArtistaRepository(private val application: Application) {

    fun refreshData(callback: (List<Artista>) -> Unit,onFailure: (String) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getArtists({
            if (it.code() == 200 && it.body() != null){
                callback(it.body()!!)
            } else {
                callback(emptyList())
            }
        }, onFailure)
    }
}