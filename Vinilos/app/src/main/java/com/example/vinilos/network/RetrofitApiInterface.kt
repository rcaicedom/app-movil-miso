package com.example.vinilos.network

import com.example.vinilos.data.album.Album
import com.example.vinilos.data.artista.Artista
import com.example.vinilos.data.album.AlbumDetalle
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiInterface {

    @GET("/albums")
    fun getAlbums(): Call<List<Album>>

    @GET("/musicians")
    fun getArtists(): Call<List<Artista>>

    @GET("/albums/{idAlbum}")
    fun getAlbum(@Path("idAlbum") idAlbum: Int): Call<AlbumDetalle>
}