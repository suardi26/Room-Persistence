package com.practice.room_persistence.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.room_persistence.dao.NoteDao
import com.practice.room_persistence.entity.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteDao: NoteDao
) : ViewModel() {


    val notes: LiveData<List<Note>> = noteDao.getListNotes()

    // Add Note
    fun addNewNote(textNote: String) = insertDatabase(textNote)
    private fun insertDatabase(textNote: String) {
        viewModelScope.launch {
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

    fun getNote(noteId: Int) {
        viewModelScope.launch {
            _note.postValue(noteDao.getNote(noteId))
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.updateNote(note)
        }
    }

    // Delete Note
    fun delete(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }

}