package com.neteinstein.alfie

import android.app.Application
import com.neteinstein.alfie.di.initKoin

class MuseumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
