package com.example.vinilos.data.artista

data class Artista(
    val id:Int,
    val name:String,
    val image:String,
    val description:String,
    val birthDate:String? = null,
    val creationDate:String? = null
)
