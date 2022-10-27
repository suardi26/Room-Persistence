package com.practice.room_persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.practice.room_persistence.entity.Note

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Query("SELECT * FROM notes")
    fun getListNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id =:noteId")
    fun getNote(noteId: Int): Note

    @Update
    fun updateNote(note: Note)

    @Delete
    fun delete(note: Note): Int
}