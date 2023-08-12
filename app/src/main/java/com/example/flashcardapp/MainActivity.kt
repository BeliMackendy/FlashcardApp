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

        val txt_A = findViewById<TextView>(R.id.txt_A)
        val txt_B = findViewById<TextView>(R.id.txt_B)
        val txt_C = findViewById<TextView>(R.id.txt_C)

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

        txt_A.setOnClickListener(View.OnClickListener {
            if(txt_A.text==flashcard_answer.text)
            {
                txt_A.setBackground(getResources().getDrawable(R.drawable.card_background_answer, null))
                txt_B.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
                txt_C.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
            }
            else{
                txt_A.setBackground(getResources().getDrawable(R.drawable.card_background_error, null))
                txt_B.setBackground(getResources().getDrawable(R.drawable.card_background_answer, null))
                txt_C.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
            }
        })

        txt_B.setOnClickListener(View.OnClickListener {
            if(txt_B.text==flashcard_answer.text)
            {
                txt_A.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
                txt_B.setBackground(getResources().getDrawable(R.drawable.card_background_answer, null))
                txt_C.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
            }
            else{
                txt_A.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
                txt_B.setBackground(getResources().getDrawable(R.drawable.card_background_error, null))
                txt_C.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
            }
        })

        txt_C.setOnClickListener(View.OnClickListener {
            if(txt_C.text==flashcard_answer.text)
            {
                txt_A.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
                txt_B.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
                txt_C.setBackground(getResources().getDrawable(R.drawable.card_background_answer, null))
            }
            else{
                txt_A.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
                txt_B.setBackground(getResources().getDrawable(R.drawable.card_background_answer, null))
                txt_C.setBackground(getResources().getDrawable(R.drawable.card_background_error, null))
            }
        })

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result->
            val data: Intent? = result.data

            if(data!=null){
                val question = data.getStringExtra("question")
                val answer = data.getStringExtra("answer")
                val option1 = data.getStringExtra("option1")
                val option2 = data.getStringExtra("option2")

                Log.i("TAG", "Question:$question ")
                Log.i("TAG", "Answer:$answer ")

                flashcard_question.text = question
                flashcard_answer.text = answer
                txt_A.text = option1
                txt_B.text = answer
                txt_C.text = option2

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