package com.example.schooljournal

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import androidx.multidex.MultiDex
import com.example.rest.di.*
import com.example.schooljournal.di.*

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            modules(
                listOf(
                    mainViewModelModule,
                    scheduleCreateViewModelModule,
                    editDayViewModelModule,
                    weekDayViewModelModule,
                    initialViewModelModule,
                    subjectRepository,
                    databaseModule,
                    gifViewModelModule,
                    gifRepository,
                    netModule,
                    apiModule
                )
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}