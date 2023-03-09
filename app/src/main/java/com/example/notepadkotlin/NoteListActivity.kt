package com.example.notepadkotlin

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes:MutableList<Note>
    lateinit var adapter: NoteAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        notes = mutableListOf<Note>()
        notes.add(Note("Naca tu forces", "Naca a pull le github"))
        notes.add(Note("Android c'est  quoi", "Android c'est cool ou pas"))
        notes.add(Note("C'est de la merde", "Naca a pull le gitLab"))
        notes.add(Note("Même pas en rêves", "Alban doit payer tout les pichets de la promo jusq'à la fin de l'année"))

        adapter = NoteAdapter(notes, this)
        val recyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onClick(view : View) {
        if (view.tag != null)
            //Log.i("NoteActivity", "Click sur note")
            showNoteDetail(view.tag as Int)
    }

    fun showNoteDetail(noteIndex: Int) {
        val note = notes[noteIndex]
        val intent = Intent(this, DetailNoteActivity::class.java)

        intent.putExtra(DetailNoteActivity.EXTRA_NOTE, note)
        intent.putExtra(DetailNoteActivity.EXTRA_NOTE_INDEX, noteIndex)
        startActivity(intent)
    }
}