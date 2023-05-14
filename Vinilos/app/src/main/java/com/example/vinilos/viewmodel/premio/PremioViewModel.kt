package com.example.vinilos.viewmodel.premio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.premio.Premio
import com.example.vinilos.data.premio.PremioRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PremioViewModel(application: Application, idPremio: Int) : AndroidViewModel(application) {

    private val premioRepository = PremioRepository(application)

    private val _premio = MutableLiveData<Premio>()

    val premio: LiveData<Premio>
        get() = _premio

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
        refreshDataFromNetwork(idPremio)
    }

    private fun refreshDataFromNetwork(idPremio: Int) {
        try{
            viewModelScope.launch(Dispatchers.Default + coroutineExceptionHandler) {
                withContext(Dispatchers.IO) {
                    val data = premioRepository.getPrize(idPremio).value
                    _premio.postValue(data)
                }
                _eventNetworkError.postValue(false)
                _isNetworkErrorShown.postValue(false)
            }
        }
        catch (e:Exception){
            _eventNetworkError.postValue(true)
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val idPremio: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PremioViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PremioViewModel(app, idPremio) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}