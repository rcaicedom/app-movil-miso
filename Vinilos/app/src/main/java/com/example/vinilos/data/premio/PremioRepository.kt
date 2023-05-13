package com.example.vinilos.data.premio

import android.app.Application
import com.example.vinilos.network.NetworkServiceAdapter

class PremioRepository(private val application: Application) {

    fun getPrize(idPrize: Int, callBack: (Premio?) -> Unit, onFailure: (String) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getPrize(idPrize, {
            if (it.code() == 200 && it.body() != null) {
                callBack(it.body()!!)
            } else {
                callBack(null)
            }
        }, onFailure)
    }
}