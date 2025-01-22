package com.example.todo_app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todo_app.model.Note

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)
    @Delete
    suspend fun deleteNote(note: Note)
    @Update
    suspend fun updateNote(note: Note)
    @Query(value = "SELECT * FROM notes_table ORDER BY id DESC")
    fun getAllNotes() : LiveData<List<Note>>
    @Query("SELECT * FROM notes_table WHERE note_title LIKE :querySearch OR note_body LIKE :querySearch")
    fun searchNote(querySearch: Query) : LiveData<List<Note>>
}