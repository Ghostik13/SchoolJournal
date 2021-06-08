package com.example.rest.di

import android.app.Application
import com.example.rest.Constants
import com.example.rest.data.GifRepositoryImpl
import com.example.rest.domain.GifRepository
import com.example.rest.presentation.GifDetailViewModel
import com.example.rest.presentation.GifViewModel
import com.example.rest.remote.GiphyApi
import okhttp3.Cache
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val gifViewModelModule = module {
    viewModel { GifViewModel(get()) }
}

val gifDetailViewModelModule = module {
    viewModel { GifDetailViewModel() }
}

val gifRepository = module {
    fun provideRepository(api: GiphyApi): GifRepositoryImpl {
        return GifRepositoryImpl(api)
    }
    single<GifRepository> { provideRepository(get()) }
}

val apiModule = module {
    fun provideGiphyApi(retrofit: Retrofit): GiphyApi {
        return retrofit.create(GiphyApi::class.java)
    }

    single { provideGiphyApi(get()) }
}

val netModule = module {
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    single { provideRetrofit() }
    single { provideCache(get()) }
}