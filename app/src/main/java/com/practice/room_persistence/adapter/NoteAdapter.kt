package com.practice.room_persistence.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.room_persistence.databinding.ItemNoteBinding
import com.practice.room_persistence.entity.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    interface OnItemClickCallback{
        fun onItemClicked(data: Note)
    }

    private var listener: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.listener = onItemClickCallback
    }

    private val differCallback = object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }

    val differ: AsyncListDiffer<Note> = AsyncListDiffer(this, differCallback)

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(note: Note){
                with(binding){
                   textViewNote.text = note.note
                    root.setOnClickListener {
                        listener?.onItemClicked(note)
                    }
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int){
        val note = differ.currentList[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int  = differ.currentList.size
}