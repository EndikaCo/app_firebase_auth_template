package com.endcodev.beautifullogin.domain

import android.util.Log

object App {
    val tag: String
        get() {
            var tag = ""
            val ste = Thread.currentThread().stackTrace
            for (i in ste.indices) {
                if (ste[i].methodName == "getTag") {
                    tag = "(" + ste[i + 1].fileName + ":" + ste[i + 1].lineNumber + ")"
                }
            }
            return tag
        }

    fun logV(message: String) {
        Log.v(tag, message)}
}