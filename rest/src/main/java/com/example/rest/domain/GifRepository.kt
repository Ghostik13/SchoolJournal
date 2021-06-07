package com.example.rest.domain

import com.example.rest.data.model.GifRemote

interface GifRepository {
    suspend fun loadGifs(query: String): GifRemote
    suspend fun loadTrendGifs(): GifRemote
}