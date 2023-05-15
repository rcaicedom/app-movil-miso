package com.example.vinilos.data.artista

import com.example.vinilos.data.album.Album
import com.example.vinilos.data.premio.Premio


data class ArtistaDetalle (
    val id:Int,
    val name:String,
    val image:String,
    val description:String,
    val birthDate:String?,
    val creationDate:String?,
    val albums:List<Album>,
    val musicians: List<Artista>?,
    val performerPrizes: List<Premio>
)