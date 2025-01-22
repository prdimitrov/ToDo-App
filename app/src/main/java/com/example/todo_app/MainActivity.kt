package com.example.todo_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todo_app.database.NoteDatabase
import com.example.todo_app.databinding.ActivityMainBinding
import com.example.todo_app.repository.NoteRepository
import com.example.todo_app.viewmodel.NoteViewModel
import com.example.todo_app.viewmodel.viewmodelfactory.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(NoteDatabase(this))
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)

        noteViewModel =
            ViewModelProvider(this, viewModelProviderFactory)
                .get(NoteViewModel::class.java)

    }
}