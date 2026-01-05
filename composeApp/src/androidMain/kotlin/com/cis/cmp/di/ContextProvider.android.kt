package com.cis.cmp.di

import android.content.Context as AndroidContext

class AndroidPlatformContext(val context: AndroidContext) : PlatformContext

private var platformContext: AndroidPlatformContext? = null

fun initializeContext(context: AndroidContext) {
    platformContext = AndroidPlatformContext(context.applicationContext)
}

actual fun getPlatformContext(): PlatformContext {
    return platformContext
        ?: throw IllegalStateException("Android context not initialized")
}