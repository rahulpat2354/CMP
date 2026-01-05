package com.cis.cmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform