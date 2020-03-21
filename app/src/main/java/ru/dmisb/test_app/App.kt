package ru.dmisb.test_app

import android.app.Application
import ru.dmisb.test_app.domain.Repository

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Repository.init(applicationContext)
    }
}