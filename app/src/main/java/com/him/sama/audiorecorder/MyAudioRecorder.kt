package com.him.sama.audiorecorder

import android.app.Application
import com.him.sama.audiorecorder.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyAudioRecorder : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            this.androidContext(this@MyAudioRecorder)
            modules(appModule)
        }
    }
}