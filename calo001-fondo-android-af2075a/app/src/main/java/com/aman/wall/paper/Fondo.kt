package com.aman.wall.paper

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.aman.wall.paper.manager.sharedpreferences.FondoSharePreferences

class Fondo : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: Fondo? = null
        fun getInstance(): Fondo = instance as Fondo
    }

    override fun onCreate() {
        super.onCreate()

        val current = getCurrentDarkMode()

        AppCompatDelegate.setDefaultNightMode(current)
    }

    private fun getCurrentDarkMode(): Int = FondoSharePreferences.getDarkMode()
}