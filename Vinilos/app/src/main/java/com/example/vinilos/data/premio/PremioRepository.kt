package com.example.vinilos.data.premio

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.vinilos.network.NetworkServiceAdapter

class PremioRepository(private val application: Application) {

    suspend fun getPrize(idPrize: Int): LiveData<Premio> {
        return NetworkServiceAdapter.getInstance(application).getPrize(idPrize)
    }
}