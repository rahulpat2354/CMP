package com.cis.cmp.di

class IosPlatformContext : PlatformContext

actual fun getPlatformContext(): PlatformContext {
    return IosPlatformContext()
}