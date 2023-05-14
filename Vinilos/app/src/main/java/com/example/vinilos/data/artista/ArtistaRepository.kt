package com.example.vinilos.data.artista

import android.app.Application
import com.example.vinilos.network.NetworkServiceAdapter

class ArtistaRepository(private val application: Application) {

    fun refreshData(callback: (List<Artista>) -> Unit, onFailure: (String) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getArtists({
            if (it.code() == 200 && it.body() != null) {
                callback(it.body()!!)
            } else {
                callback(emptyList())
            }
        }, onFailure)
    }

    fun getBands(callback: (List<Artista>) -> Unit, onFailure: (String) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getBands({
            if (it.code() == 200 && it.body() != null) {
                callback(it.body()!!)
            } else {
                callback(emptyList())
            }
        }, onFailure)
    }

    fun getArtist(idArtist: Int, callBack: (ArtistaDetalle?) -> Unit, onFailure: (String) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getArtist(idArtist, {
            if (it.code() == 200 && it.body() != null) {
                callBack(it.body()!!)
            } else {
                callBack(null)
            }
        }, onFailure)
    }

    fun getBand(idBand: Int, callBack: (ArtistaDetalle?) -> Unit, onFailure: (String) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getBand(idBand, {
            if (it.code() == 200 && it.body() != null) {
                callBack(it.body()!!)
            } else {
                callBack(null)
            }
        }, onFailure)
    }
}