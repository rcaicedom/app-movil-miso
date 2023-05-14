package com.example.vinilos.data.coleccionista

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.vinilos.network.NetworkServiceAdapter

class ColeccionistaRepository(private val application: Application) {

    suspend fun refreshData(): LiveData<List<Coleccionista>> {
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }

    suspend fun getCollector(idCollector: Int): LiveData<ColeccionistaDetalle> {
        return NetworkServiceAdapter.getInstance(application).getCollector(idCollector)
    }
}