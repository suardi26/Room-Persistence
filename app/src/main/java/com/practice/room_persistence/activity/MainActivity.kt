package com.practice.room_persistence.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.room_persistence.adapter.NoteAdapter
import com.practice.room_persistence.databinding.ActivityMainBinding
import com.practice.room_persistence.entity.Note
import com.practice.room_persistence.model.NoteViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model: NoteViewModel by viewModels()
    private val noteAdapter = NoteAdapter()
    private val getAction =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResultRegistry ->
            if (activityResultRegistry.resultCode == CreateNoteActivity.RESULT_CODE) {
                val note: Note = activityResultRegistry?.data?.getParcelableExtra<Note>(CreateNoteActivity.NEW_NOTE) as Note
                    model.addNewNote(note.note)
                    Toast.makeText(this, "User Berhasil Ditambahkan !", Toast.LENGTH_LONG).show()

            } else if (activityResultRegistry.resultCode == EditNoteActivity.RESULT_CODE){
                val note: Note = activityResultRegistry?.data?.getParcelableExtra<Note>(EditNoteActivity.UPDATE_NOTE) as Note
                model.updateNote(note)
                Toast.makeText(this, "User Berhasil Di Update", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showListNotes()

    }

    private fun showListNotes() {
        binding.apply {
            recyclerViewNote.setHasFixedSize(true)
            recyclerViewNote.layoutManager = LinearLayoutManager(this@MainActivity)

            model.notes.observe(this@MainActivity) {
                it?.let { noteAdapter.differ.submitList(it) }
                recyclerViewNote.adapter = noteAdapter
            }

            noteAdapter.setOnItemClickCallback(object: NoteAdapter.OnItemClickCallback{
                override fun onItemClicked(data: Note) {
                    Intent(this@MainActivity, EditNoteActivity::class.java).also {
                        Log.v("Test ID", data.id.toString())
                        it.putExtra(EditNoteActivity.USER_ID, data.id)
                        getAction.launch(it)
                    }
                }
            })

            addFloatingButton.setOnClickListener{
                Intent(this@MainActivity, CreateNoteActivity::class.java).also{
                    getAction.launch(it)
                }
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val note: Note = noteAdapter.differ.currentList[viewHolder.layoutPosition]
                    model.delete(note)
                    Toast.makeText(this@MainActivity, "Note Berhasil Dihapus !", Toast.LENGTH_LONG).show()
                }
            }).attachToRecyclerView(recyclerViewNote)
        }
    }

}