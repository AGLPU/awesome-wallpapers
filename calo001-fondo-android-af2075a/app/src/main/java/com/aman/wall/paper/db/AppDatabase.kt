package com.aman.wall.paper.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aman.wall.paper.model.SearchHistory
import com.aman.wall.paper.util.Converters

@Database(entities = [SearchHistory::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        val DB_NAME = "fondo"
        fun getInstance(context: Context): AppDatabase =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, DB_NAME).build()
    }

    abstract fun searchHistoryDao(): SearchHistoryDao
}

object AppDBProvider {
    private var appDatabase: AppDatabase? = null
    fun getInstance(context: Context): AppDatabase {
        if (appDatabase == null) {
            appDatabase = AppDatabase.getInstance(context)
        }
        return appDatabase!!
    }
}