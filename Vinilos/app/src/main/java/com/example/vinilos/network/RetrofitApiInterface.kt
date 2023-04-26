package com.example.vinilos.network

import com.example.vinilos.data.album.Album
import com.example.vinilos.data.artista.Artista
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiInterface {

    @GET("/albums")
    fun getAlbums(): Call<List<Album>>

    @GET("/musicians")
    fun getArtists(): Call<List<Artista>>
}