package com.cis.cmp

import android.app.Application
import com.cis.cmp.di.initializeContext

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initializeContext(this)
    }
}