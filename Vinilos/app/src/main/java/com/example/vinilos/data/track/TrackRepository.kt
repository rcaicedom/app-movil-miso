package com.example.vinilos.data.track

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter

class TrackRepository(private val application: Application) {

    suspend fun addTrackToAlbum(idAlbum: Int, track: Track): Boolean {
        val data = NetworkServiceAdapter.getInstance(application).addTrackToAlbum(idAlbum, track)
        if(data){
            CacheManager.getInstance(application.applicationContext).removeAlbum(idAlbum)
        }
        return data
    }

}