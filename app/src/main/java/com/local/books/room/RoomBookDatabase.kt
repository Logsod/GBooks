package com.local.books.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BookEntity::class],
    version = 1
)
abstract class RoomBookDatabase() : RoomDatabase() {
    abstract fun bookDao(): BookDao

//    companion object {
//        var INSTANCE: RoomBookDatabase? = null
//
//        fun getRoomBookDatabase(context: Context): RoomBookDatabase? {
//            if (INSTANCE == null) {
//                synchronized(RoomBookDatabase::class) {
//                    INSTANCE = Room.databaseBuilder(
//                        context.applicationContext,
//                        RoomBookDatabase::class.java,
//                        "myDB"
//                    ).build()
//                }
//            }
//            return INSTANCE
//        }
//
//        fun destroyDataBase() {
//            INSTANCE = null
//        }
//    }
}