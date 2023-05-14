package com.example.vinilos.data.album

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.vinilos.network.NetworkServiceAdapter

class AlbumRepository(private val application: Application) {
    suspend fun refreshData(): LiveData<List<Album>> {
        return NetworkServiceAdapter.getInstance(application).getAlbums()
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