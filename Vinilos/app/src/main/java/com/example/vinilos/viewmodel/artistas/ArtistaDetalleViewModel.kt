package com.example.vinilos.viewmodel.artistas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.artista.ArtistaDetalle
import com.example.vinilos.data.artista.ArtistaRepository


class ArtistaDetalleViewModel(application: Application, idArtist: Int, esMusico: Boolean) : AndroidViewModel(application) {

    private val artistaRepository = ArtistaRepository(application)

    private val _artista = MutableLiveData<ArtistaDetalle>()

    val artista: LiveData<ArtistaDetalle>
        get() = _artista

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork(idArtist, esMusico)
    }

    private fun refreshDataFromNetwork(idArtist: Int, esMusico: Boolean) {
        if(esMusico) {
            artistaRepository.getArtist(idArtist, {
                _artista.postValue(it)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            }, {
                _eventNetworkError.value = true
            })
        } else {
            artistaRepository.getBand(idArtist, {
                _artista.postValue(it)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            }, {
                _eventNetworkError.value = true
            })
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val idArtist: Int, private val esMusico: Boolean) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistaDetalleViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistaDetalleViewModel(app, idArtist, esMusico) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}