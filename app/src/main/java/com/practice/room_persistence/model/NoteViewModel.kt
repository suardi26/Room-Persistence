package com.practice.room_persistence.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.practice.room_persistence.dao.NoteDao
import com.practice.room_persistence.database.AppDatabase
import com.practice.room_persistence.entity.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao: NoteDao by lazy {
        Room.databaseBuilder(
            application,
            AppDatabase::class.java, "database_name"
        ).build().noteDao()
    }

    val notes: LiveData<List<Note>> = noteDao.getListNotes()

    // Add Note
    fun addNewNote(textNote: String) = insertDatabase(textNote)
    private fun insertDatabase(textNote: String) {
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.insert(
                Note(
                    0,
                    textNote
                )
            )
        }
    }

    // Edit Note
    // object yang menampung data _note dari database
    private val _note = MutableLiveData<Note>()

    // object yang di-observe "note"
    val note: LiveData<Note> get() = _note

    fun getNote(noteId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            _note.postValue(noteDao.getNote(noteId))
        }
    }
    fun updateNote(note: Note){
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.updateNote(note)
        }
    }

    // Delete Note
    fun delete(note: Note){
        CoroutineScope(Dispatchers.IO).launch {
            noteDao.delete(note)
        }
    }

}