package com.example.rightdeedtest.data.remote

import com.example.rightdeedtest.model.data.CodeResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("getCode")
    suspend fun getCode(@Query("login") login: String): CodeResponseData

    @GET("getToken")
    suspend fun getToken(@Query("login") login: String, @Query("password") password: String): String
}