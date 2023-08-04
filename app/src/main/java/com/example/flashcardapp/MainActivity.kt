package com.example.flashcardapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.flashcard_question).setOnClickListener(View.OnClickListener {
            findViewById<TextView>(R.id.flashcard_question).visibility = View.INVISIBLE
            findViewById<TextView>(R.id.flashcard_answer).visibility = View.VISIBLE
        })

        findViewById<TextView>(R.id.flashcard_answer).setOnClickListener(View.OnClickListener {
            findViewById<TextView>(R.id.flashcard_question).visibility = View.VISIBLE
            findViewById<TextView>(R.id.flashcard_answer).visibility = View.INVISIBLE
        })
    }
}