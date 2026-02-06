package com.mindera.alfie

import android.app.Application
import com.mindera.alfie.di.initKoin

class MuseumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
