package com.example.vinilos.data.coleccionista

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter

class ColeccionistaRepository(private val application: Application) {

    suspend fun refreshData(): LiveData<List<Coleccionista>> {
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }

    suspend fun getCollector(idCollector: Int): ColeccionistaDetalle {
        val potentialResp = CacheManager.getInstance(application.applicationContext).getCollector(idCollector)
        return if(potentialResp == null){
            val collector = NetworkServiceAdapter.getInstance(application).getCollector(idCollector)
            CacheManager.getInstance(application.applicationContext).addCollector(idCollector, collector!!)
            collector
        } else {
            potentialResp
        }
    }
}