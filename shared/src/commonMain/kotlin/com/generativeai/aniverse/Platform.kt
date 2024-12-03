package com.generativeai.aniverse

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform