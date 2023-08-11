package com.example.flashcardapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flashcard_question = findViewById<TextView>(R.id.flashcard_question)
        val flashcard_answer = findViewById<TextView>(R.id.flashcard_answer)
        val btAdd = findViewById<ImageView>(R.id.bt_add)
        val btEdit = findViewById<ImageView>(R.id.bt_edit)

        flashcard_question.setOnClickListener(View.OnClickListener {
            flashcard_question.visibility = View.INVISIBLE
            flashcard_answer.visibility = View.VISIBLE
        })

        flashcard_answer.setOnClickListener(View.OnClickListener {
            flashcard_question.visibility = View.VISIBLE
            flashcard_answer.visibility = View.INVISIBLE
        })

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            val data: Intent? = result.data

            if(data!=null){
                val question = data.getStringExtra("question")
                val answer = data.getStringExtra("answer")

                Log.i("TAG", "Question:$question ")
                Log.i("TAG", "Answer:$answer ")

                flashcard_question.text = question
                flashcard_answer.text = answer

                Snackbar.make(findViewById(R.id.flashcard_question),
                    "Card successfylly created",
                    Snackbar.LENGTH_SHORT)
                    .show()
            }
            else{
                Log.i("TAG", "Returned null data from AddCardActivity ")
            }
        }

        btAdd.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,AddCardActivity::class.java)
            resultLauncher.launch(intent)
        })

        btEdit.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,AddCardActivity::class.java)
            intent.putExtra("question",flashcard_question.text)
            intent.putExtra("answer",flashcard_answer.text)
            resultLauncher.launch(intent)
        })
    }
}