package com.example.todo_app.adapter

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_app.R
import com.example.todo_app.databinding.NoteLayoutBinding
import com.example.todo_app.model.Note
import java.util.Random

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun getDiffer(): AsyncListDiffer<Note> {
        return differ
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = differ.currentList[position]
        // Bind the note data to the view
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // NoteViewHolder class to bind the views
    class NoteViewHolder(private val itemBinding: NoteLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        // Function to bind note data to views
        fun bind(note: Note) {
            itemBinding.noteLayoutNoteTitleTextView.text = note.noteTitle
            itemBinding.tvNoteBody.text = note.noteBody
            val random = Random()
            val color =
                Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))

            itemBinding.ibColor.setBackgroundColor(color)

            // Set onClickListener to navigate to UpdateNoteFragment
            itemView.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("note", note)  // Assuming Note implements Parcelable
                }
                it.findNavController()
                    .navigate(R.id.action_homeFragment_to_updateNoteFragment, bundle)
            }
        }
    }
}