package com.example.vinilos.viewmodel.premio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.data.premio.Premio
import com.example.vinilos.data.premio.PremioRepository

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

    init {
        refreshDataFromNetwork(idPremio)
    }

    private fun refreshDataFromNetwork(idPremio: Int) {
        premioRepository.getPrize(idPremio, {
            _premio.postValue(it)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }, {
            _eventNetworkError.value = true
        })
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application, private val idPremio: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PremioViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PremioViewModel(app, idPremio) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}