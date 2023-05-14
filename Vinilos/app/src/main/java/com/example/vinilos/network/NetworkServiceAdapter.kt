package com.example.vinilos.network

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinilos.data.album.Album
import com.example.vinilos.data.album.AlbumDetalle
import com.example.vinilos.data.artista.Artista
import com.example.vinilos.data.artista.ArtistaDetalle
import com.example.vinilos.data.coleccionista.Coleccionista
import com.example.vinilos.data.coleccionista.ColeccionistaDetalle
import com.example.vinilos.data.premio.Premio
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


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

    suspend fun getAlbums() = suspendCoroutine<LiveData<List<Album>>> {
        val data = MutableLiveData<List<Album>>()
        retrofitApiInterface.getAlbums().enqueue(object : Callback<List<Album>> {
            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                if (response.code() == 200 && response.body() != null) {
                    data.value = response.body()!!
                } else {
                    data.value = emptyList()
                }
                it.resume(data)
            }
        })
    }

    suspend fun getArtists() = suspendCoroutine<LiveData<List<Artista>>> {
        val data = MutableLiveData<List<Artista>>()
        retrofitApiInterface.getArtists().enqueue(object : Callback<List<Artista>> {
            override fun onFailure(call: Call<List<Artista>>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<List<Artista>>, response: Response<List<Artista>>) {
                if (response.code() == 200 && response.body() != null){
                    data.value = response.body()!!
                } else {
                    data.value = emptyList()
                }
                it.resume(data)
            }
        })
    }

    fun getAlbum( idAlbum: Int) {
        retrofitApiInterface.getAlbum(idAlbum).enqueue(object : Callback<AlbumDetalle> {
            override fun onFailure(call: Call<AlbumDetalle>, t: Throwable) {
                onFailure("Error al cargar la informacion")
            }

            override fun onResponse(call: Call<AlbumDetalle>, response: Response<AlbumDetalle>) {
                onResponse(response)
            }
        })
    }

    fun getCollectors(
        onResponse: (resp: Response<List<Coleccionista>>) -> Unit,
        onFailure: (resp: String) -> Unit
    ) {
        retrofitApiInterface.getCollectors().enqueue(object : Callback<List<Coleccionista>> {
            override fun onFailure(call: Call<List<Coleccionista>>, t: Throwable) {
                onFailure("No se encontraron coleccionistas, intente mas tarde")
            }

            override fun onResponse(call: Call<List<Coleccionista>>, response: Response<List<Coleccionista>>) {
                onResponse(response)
            }
        })
    }

    fun getCollector(
        idCollector: Int,
        onResponse: (resp: Response<ColeccionistaDetalle>) -> Unit,
        onFailure: (resp: String) -> Unit
    ) {
        retrofitApiInterface.getCollector(idCollector).enqueue(object : Callback<ColeccionistaDetalle> {
            override fun onFailure(call: Call<ColeccionistaDetalle>, t: Throwable) {
                onFailure("Error al cargar la informacion")
            }

            override fun onResponse(call: Call<ColeccionistaDetalle>, response: Response<ColeccionistaDetalle>) {
                onResponse(response)
            }
        })
    }

    fun getBands(
        onResponse: (resp: Response<List<Artista>>) -> Unit,
        onFailure: (resp: String) -> Unit
    ) {
        retrofitApiInterface.getBands().enqueue(object : Callback<List<Artista>> {
            override fun onFailure(call: Call<List<Artista>>, t: Throwable) {
                onFailure("No se encontraron bandas, intente mas tarde")
            }
            override fun onResponse(call: Call<List<Artista>>, response: Response<List<Artista>>) {
                onResponse(response)
            }
        })
    }

    fun getArtist(
        idMusician: Int,
        onResponse: (resp: Response<ArtistaDetalle>) -> Unit,
        onFailure: (resp: String) -> Unit
    ) {
        retrofitApiInterface.getArtist(idMusician).enqueue(object : Callback<ArtistaDetalle> {
            override fun onFailure(call: Call<ArtistaDetalle>, t: Throwable) {
                onFailure("Error al cargar la informacion")
            }

            override fun onResponse(call: Call<ArtistaDetalle>, response: Response<ArtistaDetalle>) {
                onResponse(response)
            }
        })
    }

    fun getBand(
        idBand: Int,
        onResponse: (resp: Response<ArtistaDetalle>) -> Unit,
        onFailure: (resp: String) -> Unit
    ) {
        retrofitApiInterface.getBand(idBand).enqueue(object : Callback<ArtistaDetalle> {
            override fun onFailure(call: Call<ArtistaDetalle>, t: Throwable) {
                onFailure("Error al cargar la informacion")
            }

            override fun onResponse(call: Call<ArtistaDetalle>, response: Response<ArtistaDetalle>) {
                onResponse(response)
            }
        })
    }

    fun getPrize(
        idPrize: Int,
        onResponse: (resp: Response<Premio>) -> Unit,
        onFailure: (resp: String) -> Unit
    ) {
        retrofitApiInterface.getPrize(idPrize).enqueue(object : Callback<Premio> {
            override fun onFailure(call: Call<Premio>, t: Throwable) {
                onFailure("Error al cargar la informacion")
            }

            override fun onResponse(call: Call<Premio>, response: Response<Premio>) {
                onResponse(response)
            }
        })
    }
}