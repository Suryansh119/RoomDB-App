package com.example.s

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.s.data.Repository
import com.example.s.data.database.ContactDao
import com.example.s.data.database.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Module {
    @Provides
    @Singleton
    fun provideDb(context: Application): ContactDatabase {
        return Room.databaseBuilder(context, ContactDatabase::class.java, "contact_table.db").setJournalMode(
            RoomDatabase.JournalMode.AUTOMATIC).fallbackToDestructiveMigration().build()
    }
    @Provides
    @Singleton
    fun provideRepository(database: ContactDatabase): Repository {
        return Repository(database.contactDao())
    }
}