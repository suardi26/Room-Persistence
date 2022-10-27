package com.practice.room_persistence.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.practice.room_persistence.databinding.ActivityEditNoteBinding
import com.practice.room_persistence.entity.Note
import com.practice.room_persistence.model.NoteViewModel

class EditNoteActivity : AppCompatActivity() {
    companion object{
        const val UPDATE_NOTE = "UPDATE"
        const val RESULT_CODE = 20
        const val USER_ID = "USER_ID"
    }
    private val model: NoteViewModel by viewModels()
    private lateinit var binding: ActivityEditNoteBinding
    // private var note: LiveData<Note>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getIntExtra(USER_ID, 0)
        Log.v("INFO ID", id.toString())

        if(savedInstanceState === null){
            model.getNote(id)
        }

        binding.apply {

                model.note.observe(this@EditNoteActivity){note ->
                   textInputEditTextNote.setText(note.note)
                }

            buttonUpdate.setOnClickListener {
                val noteString = textInputEditTextNote.text.toString()
                if (!TextUtils.isEmpty(noteString)){
                    val objectNote = Note(
                        id = id,
                        note = noteString
                    )

                    Intent().also{
                        it.putExtra(UPDATE_NOTE, objectNote)
                        setResult(RESULT_CODE, it)
                    }
                }else{
                    Toast.makeText(this@EditNoteActivity, "Data Kosong !", Toast.LENGTH_LONG).show()
                }
                finish()
            }

            buttonCancel.setOnClickListener {
                finish()
            }
       }
   }

}
