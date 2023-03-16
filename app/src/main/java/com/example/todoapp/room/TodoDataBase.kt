package com.example.todoapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1 , exportSchema = false)
abstract class TodoDataBase:RoomDatabase() {
    abstract fun getDao():NoteDao


    companion object {
        private var instance: TodoDataBase? = null

        @Synchronized
        fun getInstance(ctx: Context): TodoDataBase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, TodoDataBase::class.java,
                    "note_database"
                )

                    .build()

            return instance!!

        }
    }

}