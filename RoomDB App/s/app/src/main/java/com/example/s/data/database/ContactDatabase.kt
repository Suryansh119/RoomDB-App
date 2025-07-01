package com.example.s.data.database

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.s.data.entity.Contact

@Database(entities = [Contact::class], version = 2, exportSchema = false)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao():ContactDao

    companion object{
        var db:ContactDatabase?=null
    }
}

