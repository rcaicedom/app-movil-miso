package com.example.vinilos.viewmodel.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.album.AlbumDetalle
import com.example.vinilos.data.album.AlbumRepository

class AlbumDetalleViewModel(application: Application, idAlbum: Int) : AndroidViewModel(application) {

    private val albumRepository = AlbumRepository(application)

    private val _album = MutableLiveData<AlbumDetalle>()

    val album: LiveData<AlbumDetalle>
        get() = _album

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork(idAlbum)
    }

    private fun refreshDataFromNetwork(idAlbum: Int) {
        albumRepository.getAlbum(idAlbum, {
            _album.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, val idAlbum: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumDetalleViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumDetalleViewModel(app, idAlbum) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}