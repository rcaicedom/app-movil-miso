package com.example.vinilos.data.album

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.vinilos.network.NetworkServiceAdapter

class AlbumRepository(private val application: Application) {
    suspend fun refreshData(): List<Album> {
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun getAlbum(idAlbum: Int): LiveData<AlbumDetalle> {
        return NetworkServiceAdapter.getInstance(application).getAlbum(idAlbum)
    }
}