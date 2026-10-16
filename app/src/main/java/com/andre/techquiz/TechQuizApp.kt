package com.andre.techquiz

import android.app.Application
import com.andre.techquiz.di.AppContainer

class TechQuizApp : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(applicationContext)
    }
}