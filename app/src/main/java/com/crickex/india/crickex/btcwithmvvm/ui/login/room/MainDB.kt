package com.crickex.india.crickex.btcwithmvvm.ui.login.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.crickex.india.crickex.btcwithmvvm.ui.login.model.loginModels.Login1ModelItem


/*@Database(entities = [Login1ModelItem::class], version = 1)*/
abstract class MainDB : RoomDatabase() {
    /*abstract fun mainDao(): DaoMain

    companion object {

        private var INSTANCE: MainDB? = null
        fun getDB(context: Context): MainDB {
            if (INSTANCE == null) {
                synchronized(MainDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        MainDB::class.java,
                        "mainDB"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }*/
}