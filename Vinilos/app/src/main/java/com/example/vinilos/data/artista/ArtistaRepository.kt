package com.example.vinilos.data.artista

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.vinilos.network.NetworkServiceAdapter
import com.example.vinilos.network.CacheManager

class ArtistaRepository(private val application: Application) {

    suspend fun refreshData(): LiveData<List<Artista>> {
        return NetworkServiceAdapter.getInstance(application).getArtists()
    }

    suspend fun getBands(): LiveData<List<Artista>> {
        return NetworkServiceAdapter.getInstance(application).getBands()
    }

    suspend fun getArtist(idArtist: Int): ArtistaDetalle {
        val potentialResp = CacheManager.getInstance(application.applicationContext).getArtist(idArtist)
        return if(potentialResp == null){
            val artist = NetworkServiceAdapter.getInstance(application).getArtist(idArtist)
            CacheManager.getInstance(application.applicationContext).addArtist(idArtist, artist!!)
            artist
        } else {
            potentialResp
        }
    }

    suspend fun getBand(idBand: Int): ArtistaDetalle {
        val potentialResp = CacheManager.getInstance(application.applicationContext).getBand(idBand)
        return if(potentialResp == null){
            val band = NetworkServiceAdapter.getInstance(application).getBand(idBand)
            CacheManager.getInstance(application.applicationContext).addBand(idBand, band!!)
            band
        } else {
            potentialResp
        }
    }
}