package com.practice.room_persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practice.room_persistence.dao.NoteDao
import com.practice.room_persistence.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}