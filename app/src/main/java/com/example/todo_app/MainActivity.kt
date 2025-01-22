package com.example.todo_app

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import com.example.todo_app.adapter.NoteAdapter
import com.example.todo_app.database.NoteDatabase
import com.example.todo_app.databinding.ActivityMainBinding
import com.example.todo_app.repository.NoteRepository
import com.example.todo_app.viewmodel.NoteViewModel
import com.example.todo_app.viewmodel.viewmodelfactory.NoteViewModelFactory
import java.util.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private var noteAdapter: NoteAdapter = NoteAdapter()

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the ViewModel
        setUpViewModel()

        // Observe the LiveData after ViewModel initialization
        noteViewModel.getAllNotes().observe(this) { notes ->
            // Update UI with the new list of notes
            noteAdapter.submitList(notes)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.main.background = changeBackgroundColor()
        setContentView(binding.root)
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)

        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
    }

    private fun changeBackgroundColor(): Drawable {
        val random = Random()

        val red = random.nextInt(106) + 150
        val green = random.nextInt(106) + 150
        val blue = random.nextInt(106) + 150
        return Color.argb(255, red, green, blue).toDrawable()
    }
}
