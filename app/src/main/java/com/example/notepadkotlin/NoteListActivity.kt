package com.example.notepadkotlin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var notes:MutableList<Note>
    lateinit var adapter: NoteAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        findViewById<FloatingActionButton>(R.id.create_note_fab).setOnClickListener(this)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK || data == null){
            return
        }
        when(requestCode){
            DetailNoteActivity.REQUEST_EDIT_NOTE -> processEditNoteResult(data)
        }
    }

    private fun processEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(DetailNoteActivity.EXTRA_NOTE_INDEX, -1)
        val note = if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.TIRAMISU) {
            Log.i("Mon probleme", "je suis dans TIRAMISU")
            data.getParcelableExtra(DetailNoteActivity.EXTRA_NOTE, Note::class.java)!!
        } else {
            Log.i("Mon probleme", "je suis depreciate")
            data.getParcelableExtra<Note>(DetailNoteActivity.EXTRA_NOTE)!!
        }
        saveNote(note, noteIndex)
    }

    override fun onClick(view : View) {
        if (view.tag != null) {
            //Log.i("NoteActivity", "Click sur note")
            showNoteDetail(view.tag as Int)
        } else {
            when(view.id) {
                R.id.create_note_fab ->createNewNote()
            }
        }

    }

    fun createNewNote() {
        showNoteDetail(-1)
    }

    fun saveNote(note: Note, noteIndex: Int){
        if(noteIndex < 0 ){
            notes.add(0, note)
        } else {
            notes[noteIndex] = note
        }
        adapter.notifyDataSetChanged()
    }

    fun showNoteDetail(noteIndex: Int) {
        val note = if(noteIndex<0) Note() else notes[noteIndex]
        val intent = Intent(this, DetailNoteActivity::class.java)

        intent.putExtra(DetailNoteActivity.EXTRA_NOTE, note)
        intent.putExtra(DetailNoteActivity.EXTRA_NOTE_INDEX, noteIndex)
        //startActivity(intent)
        startActivityForResult(intent, DetailNoteActivity.REQUEST_EDIT_NOTE)
    }
}