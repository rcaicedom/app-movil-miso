package com.example.vinilos.viewmodel.coleccionistas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.coleccionista.Coleccionista
import com.example.vinilos.data.coleccionista.ColeccionistaRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException

class ColeccionistaViewModel(application: Application):  AndroidViewModel(application) {

    private val coleccionistaRepository =  ColeccionistaRepository(application)
    private val _coleccionistas = MutableLiveData<List<Coleccionista>>()

    val coleccionistas: LiveData<List<Coleccionista>>
        get() = _coleccionistas

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

    private fun refreshDataFromNetwork(){
        try{
            viewModelScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
                withContext(Dispatchers.IO) {
                    val data = coleccionistaRepository.refreshData().value
                    _coleccionistas.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.postValue(true)
        }
    }

    fun onNetworkErrorShown(){
        _isNetworkErrorShown.value = true
    }
    class Factory(val app : Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ColeccionistaViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ColeccionistaViewModel(app) as T
            }
            throw IllegalArgumentException("No se pudo construir el ViewModel")
        }
    }

}