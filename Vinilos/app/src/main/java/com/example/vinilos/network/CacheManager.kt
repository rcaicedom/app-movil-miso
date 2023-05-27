package com.example.vinilos.network

import android.content.Context
import android.util.LruCache
import com.example.vinilos.data.album.AlbumDetalle
import com.example.vinilos.data.artista.ArtistaDetalle
import com.example.vinilos.data.coleccionista.ColeccionistaDetalle

class CacheManager(context: Context) {
    companion object{
        private var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }
    private var albums: LruCache<Int, AlbumDetalle> = LruCache(4)
    private var artists: LruCache<Int, ArtistaDetalle> = LruCache(2)
    private var bands: LruCache<Int, ArtistaDetalle> = LruCache(2)
    private var collectors: LruCache<Int, ColeccionistaDetalle> = LruCache(2)


    fun addAlbum(albumId: Int, album: AlbumDetalle){
        if (albums[albumId] == null){
            albums.put(albumId, album)
        }
    }
    fun getAlbum(albumId: Int) : AlbumDetalle? {
        return if (albums[albumId]!=null) albums[albumId]!! else null
    }

    fun removeAlbum(albumId: Int) {
        if(albums[albumId] != null){
            albums.remove(albumId)
        }
    }

    fun addArtist(artistId: Int, artist: ArtistaDetalle){
        if (artists[artistId] == null){
            artists.put(artistId, artist)
        }
    }
    fun getArtist(artistId: Int) : ArtistaDetalle? {
        return if (artists[artistId]!=null) artists[artistId]!! else null
    }

    fun addBand(bandId: Int, band: ArtistaDetalle){
        if (bands[bandId] == null){
            bands.put(bandId, band)
        }
    }
    fun getBand(bandId: Int) : ArtistaDetalle? {
        return if (bands[bandId]!=null) bands[bandId]!! else null
    }

    fun addCollector(collectorId: Int, collector: ColeccionistaDetalle){
        if (collectors[collectorId] == null){
            collectors.put(collectorId, collector)
        }
    }
    fun getCollector(collectorId: Int) : ColeccionistaDetalle? {
        return if (collectors[collectorId]!=null) collectors[collectorId]!! else null
    }

}