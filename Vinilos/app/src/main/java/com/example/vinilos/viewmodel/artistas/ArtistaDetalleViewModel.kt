package com.example.vinilos.viewmodel.artistas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.artista.ArtistaDetalle
import com.example.vinilos.data.artista.ArtistaRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
        _eventNetworkError.postValue(true)
    }

    init {
        refreshDataFromNetwork(idArtist, esMusico)
    }

    private fun refreshDataFromNetwork(idArtist: Int, esMusico: Boolean) {
        if(esMusico) {
            try{
                viewModelScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
                    withContext(Dispatchers.IO) {
                        val data = artistaRepository.getArtist(idArtist)
                        _artista.postValue(data)
                    }
                    _eventNetworkError.postValue(false)
                    _isNetworkErrorShown.postValue(false)
                }
            }
            catch (e: Exception){
                _eventNetworkError.postValue(true)
            }
        } else {
            try{
                viewModelScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
                    withContext(Dispatchers.IO) {
                        val data = artistaRepository.getBand(idArtist)
                        _artista.postValue(data)
                    }
                    _eventNetworkError.postValue(false)
                    _isNetworkErrorShown.postValue(false)
                }
            }
            catch (e: Exception){
                _eventNetworkError.postValue(true)
            }
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