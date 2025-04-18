package dev.valvassori

import android.app.Application
import dev.valvassori.di.SharedKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedKoin.initKoin()
    }
}
