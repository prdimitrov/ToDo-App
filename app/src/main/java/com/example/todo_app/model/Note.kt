package com.example.todo_app.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("note_title")
    val noteTitle: String,
    @ColumnInfo("note_body")
    val noteBody: String
) : Parcelable