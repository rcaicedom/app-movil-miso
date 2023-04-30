package com.example.vinilos.data.album

import com.example.vinilos.data.artista.Artista
import com.example.vinilos.data.track.Track

data class AlbumDetalle (
    val id:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    val performers:List<Artista>,
    val tracks:List<Track>
)