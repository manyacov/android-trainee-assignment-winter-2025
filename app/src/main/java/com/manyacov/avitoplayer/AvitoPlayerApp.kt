package com.manyacov.avitoplayer

import android.app.Application
import com.manyacov.avitoplayer.di.DaggerApplicationComponent

class AvitoPlayerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent
            .builder()
            .context(this)
            .application(this)
            .build()
    }
}