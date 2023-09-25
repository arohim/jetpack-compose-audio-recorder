package com.him.sama.audiorecorder.di

import androidx.room.Room
import com.him.sama.audiorecorder.data.database.AppDatabase
import com.him.sama.audiorecorder.data.database.AudioRecordRepositoryImpl
import com.him.sama.audiorecorder.domain.repository.AudioRecordRepository
import com.him.sama.audiorecorder.ui.recorder.screen.AudioRecorderViewModel
import com.him.sama.audiorecorder.ui.playerlist.RecordListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val appModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "appdb"
        ).build()
    }
    single {
        val database = get<AppDatabase>()
        database.audioRecordDao()
    }
    singleOf(::AudioRecordRepositoryImpl) { bind<AudioRecordRepository>() }
    viewModelOf(::AudioRecorderViewModel)
    viewModelOf(::RecordListViewModel)
}