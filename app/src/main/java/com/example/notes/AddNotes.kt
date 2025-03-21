package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notes.databinding.ActivityAddNotesBinding

class AddNotes : AppCompatActivity() {

    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.submit.setOnClickListener{
            val title = binding.title.text.toString()
            val content = binding.notes.text.toString()
            submitDatabase(title,content)
        }
    }

    private fun submitDatabase(title:String, content:String){
        val instertedRowid = databaseHelper.insertNote(title,content)
        if (instertedRowid != -1L){
            Toast.makeText(applicationContext,"Note saved successfully",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@AddNotes,MainActivity::class.java))
            finish()
        }else{
            Toast.makeText(applicationContext,"Failed to save note",Toast.LENGTH_SHORT).show()
        }
    }
}


