package com.example.vinilos.data.album

import java.io.Serializable

data class Album (
    val id:Int?=null,
    var name:String?="",
    var cover:String?="",
    var releaseDate:String?="",
    var description:String?="",
    var genre:String?="",
    var recordLabel:String?=""
) : Serializable