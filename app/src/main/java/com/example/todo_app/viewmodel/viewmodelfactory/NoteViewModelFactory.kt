package com.example.todo_app.viewmodel.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo_app.repository.NoteRepository
import com.example.todo_app.viewmodel.NoteViewModel

class NoteViewModelFactory(val application: Application,
    private val noteRepository: NoteRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(application, noteRepository) as T
    }
}