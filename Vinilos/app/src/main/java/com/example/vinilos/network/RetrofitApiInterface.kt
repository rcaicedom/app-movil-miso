package com.example.vinilos.network

import com.example.vinilos.data.album.Album
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiInterface {

    @GET("/albums")
    fun getAlbums(): Call<List<Album>>

}