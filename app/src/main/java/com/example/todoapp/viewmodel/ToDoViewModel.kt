package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.room.NoteEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ToDoViewModel(val repository: TodoRepository):ViewModel() {
    val allNotes = repository.allNote
     fun insertData(noteEntity: NoteEntity) {
         viewModelScope.launch {
             repository.insertData(noteEntity)
         }
     }
         fun deleteData(noteEntity: NoteEntity){
             viewModelScope.launch {
                 repository.deleteData(noteEntity)
             }
         }
     fun updateData(noteEntity: NoteEntity){
        viewModelScope.launch {
            repository.updateData(noteEntity)
        }
    }

    }
