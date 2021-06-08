package com.example.schooljournal.di

import android.app.Application
import androidx.room.Room
import com.example.schooljournal.data.DayDao
import com.example.schooljournal.data.DayDatabase
import com.example.schooljournal.data.SubjectRepositoryImpl
import com.example.schooljournal.domain.SubjectRepository
import com.example.schooljournal.presentation.initialView.InitialViewModel
import com.example.schooljournal.presentation.mainPage.MainPageViewModel
import com.example.schooljournal.presentation.scheduleCreateView.ScheduleCreateViewModel
import com.example.schooljournal.presentation.settingsView.scheduleEditView.EditDayViewModel
import com.example.schooljournal.presentation.weekDayView.WeekDayViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

    val mainViewModelModule = module {
        viewModel { MainPageViewModel(get()) }
    }

    val scheduleCreateViewModelModule = module {
        viewModel { ScheduleCreateViewModel(get()) }
    }

    val editDayViewModelModule = module {
        viewModel { EditDayViewModel(get()) }
    }

    val weekDayViewModelModule = module {
        viewModel { WeekDayViewModel() }
    }

    val initialViewModelModule = module {
        viewModel { InitialViewModel() }
    }

    val subjectRepository = module {
        fun provideRepository(dao: DayDao): SubjectRepositoryImpl {
            return SubjectRepositoryImpl(dao)
        }
        single<SubjectRepository> { provideRepository(get()) }
    }

    val databaseModule = module {

        fun provideDatabase(application: Application): DayDatabase {
            return Room.databaseBuilder(application, DayDatabase::class.java, "days")
                .fallbackToDestructiveMigration()
                .build()
        }

        fun provideCountriesDao(database: DayDatabase): DayDao {
            return database.dayDao
        }

        single { provideDatabase(androidApplication()) }
        single { provideCountriesDao(get()) }


    }