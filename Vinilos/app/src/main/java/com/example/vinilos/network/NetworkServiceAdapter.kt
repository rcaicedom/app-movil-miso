package com.example.vinilos.network

import android.content.Context
import com.example.vinilos.data.album.Album
import com.example.vinilos.data.artista.Artista
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NetworkServiceAdapter constructor(context: Context) {
    companion object {
        const val BASE_URL = "https://back-vinilos-g3.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private var retrofit: Retrofit? = null
    private fun getRetrofitApiClient(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .build()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit!!
    }

    private val retrofitApiInterface: RetrofitApiInterface by lazy {
        getRetrofitApiClient().create(RetrofitApiInterface::class.java)
    }

    fun getAlbums(
        onResponse: (resp: Response<List<Album>>) -> Unit,
        onFailure: (resp: String) -> Unit
    ) {
        retrofitApiInterface.getAlbums().enqueue(object : Callback<List<Album>> {
            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                onFailure("Error al cargar la informacion")
            }

            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                onResponse(response)
            }
        })
    }

    fun getArtists(
        onResponse: (resp: Response<List<Artista>>) -> Unit,
        onFailure: (resp: String) -> Unit
    ) {
        retrofitApiInterface.getArtists().enqueue(object : Callback<List<Artista>> {
            override fun onFailure(call: Call<List<Artista>>, t: Throwable) {
                onFailure("No se encontraron artistas, intente mas tarde")
            }

            override fun onResponse(call: Call<List<Artista>>, response: Response<List<Artista>>) {
                onResponse(response)
            }
        })
    }
}