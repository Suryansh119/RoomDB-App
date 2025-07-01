package com.example.s.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Upsert
import com.example.s.data.entity.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Upsert
    suspend fun insertContact(contact: Contact)
    @Update
    suspend fun updateContact(contact:Contact)
    @Delete
    suspend fun deleteContact(contact:Contact)
    @Query("Select * from contact_table order by Name ASC")
    fun getContactSortByName(): Flow<List<Contact>>
    @Query("Select * from contact_table order by dateOfCreation ASC")
    fun getContactSortByCreationDate(): Flow<List<Contact>>
}