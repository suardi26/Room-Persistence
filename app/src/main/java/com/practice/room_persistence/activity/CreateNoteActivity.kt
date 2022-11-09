package com.practice.room_persistence.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.practice.room_persistence.databinding.ActivityCreateNoteBinding
import com.practice.room_persistence.entity.Note
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateNoteActivity : AppCompatActivity() {
    companion object{
        const val NEW_NOTE = "NEW_NOTE"
        const val RESULT_CODE = 10
    }

    private lateinit var binding: ActivityCreateNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonSaveNote.setOnClickListener{
                val note = textInputEditTextNote.text.toString()
                if(!TextUtils.isEmpty(note)){
                    val objectNote = Note(
                        0,
                        note
                    )
                    Intent().also {
                        it.putExtra(NEW_NOTE, objectNote)
                        setResult(RESULT_CODE, it)
                    }
                }
                else{
                    Toast.makeText(this@CreateNoteActivity, "Note Kosong !!!", Toast.LENGTH_LONG).show()
                }
                finish()
            }
        }
    }
}