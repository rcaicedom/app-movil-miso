package com.example.vinilos.viewmodel.coleccionistas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.coleccionista.ColeccionistaDetalle
import com.example.vinilos.data.coleccionista.ColeccionistaRepository

class ColeccionistaDetalleViewModel(application: Application, idCollector: Int) : AndroidViewModel(application) {

    private val coleccionistaRepository = ColeccionistaRepository(application)

    private val _coleccionista = MutableLiveData<ColeccionistaDetalle>()

    val coleccionista: LiveData<ColeccionistaDetalle>
        get() = _coleccionista

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork(idCollector)
    }

    private fun refreshDataFromNetwork(idCollector: Int) {
        coleccionistaRepository.getCollector(idCollector, {
            _coleccionista.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        },{
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val idCollector: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ColeccionistaDetalleViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ColeccionistaDetalleViewModel(app, idCollector) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}