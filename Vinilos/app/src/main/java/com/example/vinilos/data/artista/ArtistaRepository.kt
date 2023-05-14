package com.example.vinilos.data.artista

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.vinilos.network.NetworkServiceAdapter

class ArtistaRepository(private val application: Application) {

    suspend fun refreshData(): LiveData<List<Artista>> {
        return NetworkServiceAdapter.getInstance(application).getArtists()
    }

    suspend fun getBands(): LiveData<List<Artista>> {
        return NetworkServiceAdapter.getInstance(application).getBands()
    }

    suspend fun getArtist(idArtist: Int): LiveData<ArtistaDetalle> {
        return NetworkServiceAdapter.getInstance(application).getArtist(idArtist)
    }

    suspend fun getBand(idBand: Int): LiveData<ArtistaDetalle> {
        return NetworkServiceAdapter.getInstance(application).getBand(idBand)
    }
}