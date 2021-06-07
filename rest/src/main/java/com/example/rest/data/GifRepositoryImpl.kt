package com.example.rest.data

import com.example.rest.Constants.Companion.ANDROID_SDK_KEY
import com.example.rest.data.model.GifRemote
import com.example.rest.domain.GifRepository
import com.example.rest.remote.GiphyApi

class GifRepositoryImpl(private val api: GiphyApi): GifRepository {

    override suspend fun loadGifs(query: String): GifRemote {
        return api.getGifs(ANDROID_SDK_KEY, 50, query)
    }

    override suspend fun loadTrendGifs(): GifRemote {
        return api.getTrendGifs(ANDROID_SDK_KEY, 50)
    }
}