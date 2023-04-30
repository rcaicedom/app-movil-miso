package com.example.vinilos.data.album

import android.app.Application
import com.example.vinilos.network.NetworkServiceAdapter

class AlbumRepository(private val application: Application) {
    fun refreshData(callBack: (List<Album>) -> Unit, onFailure: (String) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbums({
            if (it.code() == 200 && it.body() != null) {
                callBack(it.body()!!)
            } else {
                callBack(emptyList())
            }
        }, onFailure)
    }

    fun getAlbum(idAlbum: Int, callBack: (AlbumDetalle?) -> Unit, onFailure: (String) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbum(idAlbum, {
            if (it.code() == 200 && it.body() != null) {
                callBack(it.body()!!)
            } else {
                callBack(null)
            }
        }, onFailure)
    }
}