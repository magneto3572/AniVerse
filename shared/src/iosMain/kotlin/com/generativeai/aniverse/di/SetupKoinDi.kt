package com.generativeai.aniverse.di

import com.generativeai.aniverse.data.di.dataModule
import com.generativeai.aniverse.domain.di.domainModule
import com.generativeai.aniverse.presentation.di.presentationModule
import com.generativeai.aniverse.presentation.di.uiStateModule
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin {
        modules(dataModule + domainModule + uiStateModule + presentationModule())
    }
}
