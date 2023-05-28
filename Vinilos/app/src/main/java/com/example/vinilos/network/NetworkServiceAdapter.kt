package com.example.vinilos.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinilos.data.album.Album
import com.example.vinilos.data.album.AlbumDetalle
import com.example.vinilos.data.artista.Artista
import com.example.vinilos.data.artista.ArtistaDetalle
import com.example.vinilos.data.coleccionista.Coleccionista
import com.example.vinilos.data.coleccionista.ColeccionistaDetalle
import com.example.vinilos.data.premio.Premio
import com.example.vinilos.data.track.Track
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

    suspend fun getAlbums() = suspendCoroutine {
        var data: List<Album>
        retrofitApiInterface.getAlbums().enqueue(object : Callback<List<Album>> {
            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                data = if (response.code() == 200 && response.body() != null) {
                    response.body()!!
                } else {
                    emptyList()
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

    suspend fun getAlbum( idAlbum: Int) = suspendCoroutine<AlbumDetalle?> {
        var data : AlbumDetalle?
        retrofitApiInterface.getAlbum(idAlbum).enqueue(object : Callback<AlbumDetalle> {
            override fun onFailure(call: Call<AlbumDetalle>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<AlbumDetalle>, response: Response<AlbumDetalle>) {
                data = if (response.code() == 200 && response.body() != null) {
                    response.body()!!
                } else {
                    null
                }
                it.resume(data)
            }
        })
    }

    suspend fun getCollectors() = suspendCoroutine<LiveData<List<Coleccionista>>> {
        val data = MutableLiveData<List<Coleccionista>>()
        retrofitApiInterface.getCollectors().enqueue(object : Callback<List<Coleccionista>> {
            override fun onFailure(call: Call<List<Coleccionista>>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<List<Coleccionista>>, response: Response<List<Coleccionista>>) {
                if (response.code() == 200 && response.body() != null){
                    data.value = response.body()!!
                } else {
                    data.value = emptyList()
                }
                it.resume(data)
            }
        })
    }

    suspend fun getCollector(idCollector: Int) = suspendCoroutine<ColeccionistaDetalle?> {
        var data : ColeccionistaDetalle?
        retrofitApiInterface.getCollector(idCollector).enqueue(object : Callback<ColeccionistaDetalle> {
            override fun onFailure(call: Call<ColeccionistaDetalle>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<ColeccionistaDetalle>, response: Response<ColeccionistaDetalle>) {
                data = if (response.code() == 200 && response.body() != null) {
                    response.body()!!
                } else {
                    null
                }
                it.resume(data)
            }
        })
    }

    suspend fun getBands() = suspendCoroutine<LiveData<List<Artista>>> {
        val data = MutableLiveData<List<Artista>>()
        retrofitApiInterface.getBands().enqueue(object : Callback<List<Artista>> {
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

    suspend fun getArtist(idMusician: Int) = suspendCoroutine<ArtistaDetalle?> {
        var data : ArtistaDetalle?
        retrofitApiInterface.getArtist(idMusician).enqueue(object : Callback<ArtistaDetalle> {
            override fun onFailure(call: Call<ArtistaDetalle>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<ArtistaDetalle>, response: Response<ArtistaDetalle>) {
                data = if (response.code() == 200 && response.body() != null) {
                    response.body()!!
                } else {
                    null
                }
                it.resume(data)
            }
        })
    }

    suspend fun getBand(idBand: Int) = suspendCoroutine<ArtistaDetalle?> {
        var data : ArtistaDetalle?
        retrofitApiInterface.getBand(idBand).enqueue(object : Callback<ArtistaDetalle> {
            override fun onFailure(call: Call<ArtistaDetalle>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<ArtistaDetalle>, response: Response<ArtistaDetalle>) {
                data = if (response.code() == 200 && response.body() != null) {
                    response.body()!!
                } else {
                    null
                }
                it.resume(data)
            }
        })
    }

    suspend fun getPrize(idPrize: Int) = suspendCoroutine<LiveData<Premio>> {
        val data = MutableLiveData<Premio>()
        retrofitApiInterface.getPrize(idPrize).enqueue(object : Callback<Premio> {
            override fun onFailure(call: Call<Premio>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<Premio>, response: Response<Premio>) {
                if (response.code() == 200 && response.body() != null) {
                    data.value = response.body()!!
                } else {
                    data.value = null
                }
                it.resume(data)
            }
        })
    }

    suspend fun createAlbum( album: Album) = suspendCoroutine {
        var data : Boolean
        retrofitApiInterface.createAlbum(album).enqueue(object : Callback<Album?> {
            override fun onFailure(call: Call<Album?>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<Album?>, response: Response<Album?>) {

                data = response.code() == 200 && response.body() != null
                it.resume(data)
            }
        })
    }

    suspend fun addTrackToAlbum(idAlbum: Int, track: Track) = suspendCoroutine {
        var data : Boolean
        retrofitApiInterface.addTrackToAlbum(idAlbum, track).enqueue(object : Callback<Track?> {
            override fun onFailure(call: Call<Track?>, t: Throwable) {
                it.resumeWithException(t)
            }

            override fun onResponse(call: Call<Track?>, response: Response<Track?>) {
                data = response.code() == 200 && response.body() != null
                it.resume(data)
            }
        })
    }
}