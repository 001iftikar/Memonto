package com.iftikar.memonto

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform