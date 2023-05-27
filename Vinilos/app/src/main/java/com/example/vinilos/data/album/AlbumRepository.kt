package com.example.vinilos.data.album

import android.app.Application
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter

class AlbumRepository(private val application: Application) {
    suspend fun refreshData(): List<Album> {
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun getAlbum(idAlbum: Int): AlbumDetalle {
        val potentialResp = CacheManager.getInstance(application.applicationContext).getAlbum(idAlbum)
        return if(potentialResp == null){
            val album = NetworkServiceAdapter.getInstance(application).getAlbum(idAlbum)
            CacheManager.getInstance(application.applicationContext).addAlbum(idAlbum, album!!)
            album
        } else {
            potentialResp
        }
    }

    suspend fun createAlbum(album: Album): Boolean {
        return NetworkServiceAdapter.getInstance(application).createAlbum(album)
    }
}