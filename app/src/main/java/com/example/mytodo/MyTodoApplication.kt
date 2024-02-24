package com.example.mytodo

import android.app.Application
import com.example.mytodo.di.AppContainer
import com.example.mytodo.di.AppContainerImpl

class MyTodoApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = AppContainerImpl(this)
    }

}