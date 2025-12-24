package com.edwardalarik.app.api.service

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface APIInterface {
    @Streaming
    @GET
    fun getByJson(
        @Url url: String,
    ): Call<JsonElement>
}