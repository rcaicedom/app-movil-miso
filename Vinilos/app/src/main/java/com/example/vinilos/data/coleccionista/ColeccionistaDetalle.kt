package com.example.vinilos.data.coleccionista

import com.example.vinilos.data.album.AlbumColeccionista
import com.example.vinilos.data.artista.Artista

data class ColeccionistaDetalle (
    val id:Int,
    val name:String,
    val collectorAlbums: List<AlbumColeccionista>,
    val favoritePerformers: List<Artista>
    )