package com.example.vinilos.network

import com.example.vinilos.data.album.Album
import com.example.vinilos.data.artista.Artista
import com.example.vinilos.data.album.AlbumDetalle
import com.example.vinilos.data.artista.ArtistaDetalle
import com.example.vinilos.data.coleccionista.Coleccionista
import com.example.vinilos.data.coleccionista.ColeccionistaDetalle
import com.example.vinilos.data.premio.Premio
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiInterface {

    @GET("/albums")
    fun getAlbums(): Call<List<Album>>

    @GET("/musicians")
    fun getArtists(): Call<List<Artista>>

    @GET("/albums/{idAlbum}")
    fun getAlbum(@Path("idAlbum") idAlbum: Int): Call<AlbumDetalle>

    @GET("/collectors")
    fun getCollectors(): Call<List<Coleccionista>>

    @GET("/collectors/{idCollector}")
    fun getCollector(@Path("idCollector") idCollector: Int): Call<ColeccionistaDetalle>

    @GET("/bands")
    fun getBands(): Call<List<Artista>>

    @GET("/musicians/{idMusician}")
    fun getArtist(@Path("idMusician") idMusician: Int): Call<ArtistaDetalle>

    @GET("/bands/{idBand}")
    fun getBand(@Path("idBand") idBand: Int): Call<ArtistaDetalle>

    @GET("/prizes/{idPrize}")
    fun getPrize(@Path("idPrize") idPrize: Int): Call<Premio>
}