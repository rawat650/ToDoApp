package com.example.todoapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notetable")
 class NoteEntity (@ColumnInfo(name = "title")val noteTitle :String, @ColumnInfo(name = "description")val noteDescription :String) {

    @PrimaryKey(autoGenerate = true) var id = 0
}
