package com.example.todoapp.repository

import com.example.todoapp.room.NoteDao
import com.example.todoapp.room.NoteEntity

class TodoRepository(val noteDao: NoteDao){
    val allNote = noteDao.getAllData()
    suspend fun insertData(noteEntity:NoteEntity){
        noteDao.insertData(noteEntity)
    }
    suspend fun deleteData(noteEntity: NoteEntity){
        noteDao.deleteData(noteEntity)
    }
    suspend fun updateData(noteEntity: NoteEntity){
        noteDao.updateData(noteEntity)
    }

}