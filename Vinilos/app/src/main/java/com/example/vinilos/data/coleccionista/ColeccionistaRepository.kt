package com.example.vinilos.data.coleccionista

import android.app.Application
import com.example.vinilos.network.NetworkServiceAdapter

class ColeccionistaRepository(private val application: Application) {

    fun refreshData(callback: (List<Coleccionista>) -> Unit, onFailure: (String) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getCollectors({
            if (it.code() == 200 && it.body() != null){
                callback(it.body()!!)
            } else {
                callback(emptyList())
            }
        }, onFailure)
    }
}