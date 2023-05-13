package com.example.vinilos.viewmodel.artistas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.artista.Artista
import com.example.vinilos.data.artista.ArtistaRepository
import java.lang.IllegalArgumentException

class ArtistaViewModel(application: Application) : AndroidViewModel(application){
  private val artistaRepository =  ArtistaRepository(application)
  private val _artistas = MutableLiveData<List<Artista>>()
  val artistas: LiveData<List<Artista>>
    get() = _artistas

  private val _eventNetworkError = MutableLiveData<Boolean>(false)
  val eventNetworkError: LiveData<Boolean>
    get() = _eventNetworkError

  private val _isNetworkErrorShown = MutableLiveData<Boolean>(false)
  val isNetworkErrorShown: LiveData<Boolean>
    get() = _isNetworkErrorShown

  init {
    refreshDataFromNetwork()
  }

  private fun refreshDataFromNetwork(){
    artistaRepository.refreshData({
      artistaRepository.getBands({bands ->
        _artistas.postValue(it + bands)
        _eventNetworkError.value = false
        _isNetworkErrorShown.value = false
      }, {
        _eventNetworkError.value = true
      })
    }, {
      _eventNetworkError.value = true
    })
  }

  fun onNetworkErrorShown(){
    _isNetworkErrorShown.value = true
  }
  class Factory(val app : Application): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
      if(modelClass.isAssignableFrom(ArtistaViewModel::class.java)){
        @Suppress("UNCHECKED_CAST")
        return ArtistaViewModel(app) as T
      }
      throw IllegalArgumentException("No se pudo construir el ViewModel")
    }
  }


}