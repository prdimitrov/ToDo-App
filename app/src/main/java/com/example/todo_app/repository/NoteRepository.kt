package com.example.todo_app.repository

import androidx.lifecycle.LiveData
import com.example.todo_app.database.NoteDatabase
import com.example.todo_app.model.Note

class NoteRepository(private val noteDB: NoteDatabase) {

    suspend fun insertNote(note: Note) {
        noteDB.getNoteDao().insertNote(note)
    }

    suspend fun deleteNote(note: Note) = noteDB.getNoteDao().deleteNote(note)

    suspend fun updateNote(note: Note) = noteDB.getNoteDao().updateNote(note)

    fun getAllNotes(): LiveData<List<Note>> = noteDB.getNoteDao().getAllNotes()

    fun searchNote(query: String?) = noteDB.getNoteDao().searchNote(query)
}