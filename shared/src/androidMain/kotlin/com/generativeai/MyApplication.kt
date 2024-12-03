package com.generativeai

import android.app.Application
import com.generativeai.aniverse.data.di.dataModule
import com.generativeai.aniverse.domain.di.domainModule
import com.generativeai.aniverse.presentation.di.presentationModule
import com.generativeai.aniverse.presentation.di.uiStateModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin { 
           androidContext(this@MyApplication)
            modules(dataModule + domainModule + uiStateModule + presentationModule())
        }
    }
}