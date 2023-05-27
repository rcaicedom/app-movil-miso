package com.example.vinilos.data.track

import java.io.Serializable

data class Track (
    val id: Int?=null,
    var name: String?="",
    var duration: String?=""
    ) : Serializable