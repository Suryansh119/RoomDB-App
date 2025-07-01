package com.example.s.data

import com.example.s.data.database.ContactDao
import com.example.s.data.entity.Contact

class Repository(private val contactDao: ContactDao) {
    suspend fun insertContact(contact: Contact)=contactDao.insertContact(contact)
//    suspend fun updateContact(contact: Contact)=contactDao.updateContact(contact)

    suspend fun deleteContact(contact: Contact)=contactDao.deleteContact(contact)


    fun getAllContactSortByName()=contactDao.getContactSortByName()

    fun getAllContactSortByCreationDate()=contactDao.getContactSortByCreationDate()


}