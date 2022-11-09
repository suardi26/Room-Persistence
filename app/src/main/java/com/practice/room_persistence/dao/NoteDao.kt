package com.practice.room_persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.practice.room_persistence.entity.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM notes")
    fun getListNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id =:noteId")
    suspend fun getNote(noteId: Int): Note

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun delete(note: Note)
}