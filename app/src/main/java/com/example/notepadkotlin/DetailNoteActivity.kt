package com.example.notepadkotlin

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailNoteActivity : AppCompatActivity() {
    companion object{
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "note index"
    }

    lateinit var note: Note

    var noteIndex: Int = -1

    lateinit var titleView: TextView
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_note)

        note = if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.TIRAMISU) {
            Log.i("Mon probleme", "je suis dans TIRAMISU")
            intent.getParcelableExtra(EXTRA_NOTE, Note::class.java)!!
        }
        else {
            Log.i("Mon probleme", "je suis depreciate")
            intent.getParcelableExtra<Note>(EXTRA_NOTE)!!
        }

        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)
        titleView = findViewById<TextView>(R.id.title)
        textView = findViewById<TextView>(R.id.text)

        titleView.text = note.title
        textView.text = note.text
    }
}