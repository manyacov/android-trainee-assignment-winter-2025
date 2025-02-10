package com.manyacov.avitoplayer.di

import android.app.Application
import android.content.Context
import com.manyacov.avitoplayer.AvitoPlayerApp
import com.manyacov.avitoplayer.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = []
)
interface ApplicationComponent {

    fun inject(app: AvitoPlayerApp)

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
