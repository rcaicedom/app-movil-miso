package com.example.vinilos.viewmodel.track

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.track.Track
import com.example.vinilos.data.track.TrackRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class TrackViewModel(application: Application, idAlbum: Int, track: Track) : AndroidViewModel(application) {

    private val trackRepository = TrackRepository(application)

    private val _trackCreado = MutableLiveData<Boolean>()

    val trackCreado: LiveData<Boolean>
        get() = _trackCreado

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
        refreshDataFromNetwork(idAlbum, track)
    }

    private fun refreshDataFromNetwork(idAlbum: Int, track: Track) {
        try{
            viewModelScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
                withContext(Dispatchers.IO) {
                    val data = trackRepository.addTrackToAlbum(idAlbum, track)
                    _trackCreado.postValue(data)
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

    class Factory(val app: Application, private val idAlbum: Int, private val track: Track) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TrackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TrackViewModel(app, idAlbum, track) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}