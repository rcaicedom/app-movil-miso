package com.example.vinilos.viewmodel.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.album.Album
import com.example.vinilos.data.album.AlbumRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class AlbumCreateViewModel(application: Application, album: Album) : AndroidViewModel(application) {

    private val albumRepository = AlbumRepository(application)

    private val _albumCreado = MutableLiveData<Boolean>()

    val albumCreado: LiveData<Boolean>
        get() = _albumCreado

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
        refreshDataFromNetwork(album)
    }

    private fun refreshDataFromNetwork(album: Album) {
        try{
            viewModelScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
                withContext(Dispatchers.IO) {
                    val data = albumRepository.createAlbum(album)
                    _albumCreado.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e: Exception){
            _eventNetworkError.postValue(true)
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val album: Album) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumCreateViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumCreateViewModel(app, album) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}