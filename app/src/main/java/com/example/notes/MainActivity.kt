package com.example.notes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NoteAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var noteAdapter: NoteAdapter
    private var notesList = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        setupRecyclerView()

        binding.addButton.setOnClickListener{
            startActivity(Intent(this@MainActivity,AddNotes::class.java))
        }
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(notesList, this)
        binding.notesRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }

    override fun onItemClick(note: Note) {
        // Handle note item click (implement editing functionality later)
        val intent = Intent(this, ViewNoteActivity::class.java).apply {
            putExtra("NOTE_ID", note.id)
            putExtra("NOTE_TITLE", note.title)
            putExtra("NOTE_CONTENT", note.content)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    private fun loadNotes() {
        notesList = databaseHelper.getAllNotes().toMutableList()
        noteAdapter.updateNotes(notesList)

        // Show empty view if no notes
        if (notesList.isEmpty()) {
            binding.emptyView.visibility = android.view.View.VISIBLE
            binding.notesRecyclerView.visibility = android.view.View.GONE
        } else {
            binding.emptyView.visibility = android.view.View.GONE
            binding.notesRecyclerView.visibility = android.view.View.VISIBLE
        }
    }
}