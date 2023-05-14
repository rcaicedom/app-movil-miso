package com.example.vinilos.viewmodel.coleccionistas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.coleccionista.ColeccionistaDetalle
import com.example.vinilos.data.coleccionista.ColeccionistaRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
        _eventNetworkError.postValue(true)
    }

    init {
        refreshDataFromNetwork(idCollector)
    }

    private fun refreshDataFromNetwork(idCollector: Int) {
        try{
            viewModelScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
                withContext(Dispatchers.IO) {
                    val data = coleccionistaRepository.getCollector(idCollector)
                    _coleccionista.postValue(data)
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