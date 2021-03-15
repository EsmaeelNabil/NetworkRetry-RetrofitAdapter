package com.esmaeel.networkretrycalladapterlibrary

import android.app.Application
import com.esmaeel.statelib.initNetworkStateHandler
import com.esmaeel.statelib.registerActivityTracker

/**
 * Created by Esmaeel Nabil on Mar, 2021
 */

class AppInstance : Application() {
    override fun onCreate() {
        super.onCreate()
        initNetworkStateHandler()
        registerActivityTracker()
    }
}