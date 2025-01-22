package com.example.todo_app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todo_app.MainActivity
import com.example.todo_app.R
import com.example.todo_app.databinding.FragmentUpdateNoteBinding
import com.example.todo_app.model.Note
import com.example.todo_app.viewmodel.NoteViewModel

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {
    private var binding: FragmentUpdateNoteBinding? = null
    private lateinit var notesViewModel: NoteViewModel
//    private lateinit var noteAdapter: NoteAdapter

    private lateinit var currentNote: Note

    // Since the Update Note Fragment contains arguments in nav_graph
    private val args: UpdateNoteFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        binding?.updateNoteFragmentNoteTitle?.setText(currentNote.noteTitle)
        binding?.updateNoteFragmentNoteBody?.setText(currentNote.noteBody)

        // If the user updates the note
        binding?.updateNoteFragmentFabDone?.setOnClickListener {
            val title = binding?.updateNoteFragmentNoteTitle?.text.toString().trim()
            val body = binding?.updateNoteFragmentNoteBody?.text.toString().trim()

            if (title.isNotEmpty()) {
                // Pass the same note
                val note = Note(currentNote.id, title, body)
                notesViewModel.updateNote(note)
                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            } else {
                Toast.makeText(context, "Please enter note title", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete Note")
            setMessage("Do you really want to delete this note?")
            setPositiveButton("Delete") { _, _ ->
                notesViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(R.id.action_updateNoteFragment_to_homeFragment)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}