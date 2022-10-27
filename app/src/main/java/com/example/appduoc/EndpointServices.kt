package com.example.appduoc

import okhttp3.MediaType

class EndpointServices {

    fun getService(){
        val mediatype: MediaType? = MediaType.parse("application/json; charset=utf-8")
    }
}