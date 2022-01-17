package com.wojtek.rx_java_mvvm_example

import android.app.Application
import com.wojtek.rx_java_mvvm_example.di.databaseModule
import com.wojtek.rx_java_mvvm_example.di.networkModules
import com.wojtek.rx_java_mvvm_example.di.repositoryModules
import com.wojtek.rx_java_mvvm_example.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                viewModelModules,
                networkModules,
                repositoryModules,
                databaseModule
            )
        }
    }
}