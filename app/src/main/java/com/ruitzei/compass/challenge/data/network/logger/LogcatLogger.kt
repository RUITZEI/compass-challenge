package com.ruitzei.compass.challenge.data.network.logger

import android.util.Log
import io.ktor.client.plugins.logging.Logger

class LogcatLogger : Logger {
    override fun log(message: String) {
        Log.i("Ktor", message)
    }
}