package com.example.todoapp.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun insertData(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteData(noteEntity: NoteEntity)
    @Update
    suspend fun updateData(noteEntity: NoteEntity)
@Query("Select * from notetable")
     fun getAllData():LiveData<List<NoteEntity>>
}