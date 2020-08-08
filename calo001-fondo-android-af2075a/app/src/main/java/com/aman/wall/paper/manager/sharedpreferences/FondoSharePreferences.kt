package com.aman.wall.paper.manager.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.aman.wall.paper.Fondo
import com.aman.wall.paper.config.Constants.SP_FILE
import com.aman.wall.paper.config.Constants.SP_KEY_DARK_MODE
import com.aman.wall.paper.config.Constants.SP_KEY_NOTIFICATION_COUNT

object FondoSharePreferences {
    private val sharedPreferences: SharedPreferences = Fondo.getInstance()
        .getSharedPreferences(SP_FILE, Context.MODE_PRIVATE)

    private fun getNotificationCount(): Int {
        return sharedPreferences.getInt(SP_KEY_NOTIFICATION_COUNT, 0)
    }

    private fun updateNotificationCount(value: Int) {
        sharedPreferences.edit().putInt(SP_KEY_NOTIFICATION_COUNT, value).apply()
    }

    fun getNextNotificationCount(): Int {
        val next = getNotificationCount() + 1
        updateNotificationCount(
            next
        )
        return next
    }
    fun getDarkMode(): Int {
        val systemUIMode = AppCompatDelegate.getDefaultNightMode()
        return sharedPreferences.getInt(SP_KEY_DARK_MODE, systemUIMode)
    }

    fun saveDarkMode(mode: Int) {
        sharedPreferences.edit().putInt(SP_KEY_DARK_MODE, mode).apply()
    }
}