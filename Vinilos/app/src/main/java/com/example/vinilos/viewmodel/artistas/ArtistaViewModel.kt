package com.example.vinilos.viewmodel.artistas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.artista.Artista
import com.example.vinilos.data.artista.ArtistaRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class ArtistaViewModel(application: Application) : AndroidViewModel(application) {
    private val artistaRepository = ArtistaRepository(application)
    private val _artistas = MutableLiveData<List<Artista>>()
    val artistas: LiveData<List<Artista>>
        get() = _artistas

    private val _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
        _eventNetworkError.postValue(true)
    }

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try {
            viewModelScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
                withContext(Dispatchers.IO) {
                    val data = artistaRepository.refreshData().value
                    val bandas = artistaRepository.getBands().value
                    _artistas.postValue((data!! + bandas!!))
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        } catch (e: Exception) {
            _eventNetworkError.postValue(true)
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistaViewModel(app) as T
            }
            throw IllegalArgumentException("No se pudo construir el ViewModel")
        }
    }


}