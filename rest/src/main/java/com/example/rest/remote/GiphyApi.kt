package com.example.rest.remote

import com.example.rest.data.model.GifRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("search")
    suspend fun getGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("q") category: String
    ): GifRemote

    @GET("trending")
    suspend fun getTrendGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int
    ): GifRemote
}