package com.example.flashcardapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

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
        val rl_choice = findViewById<RelativeLayout>(R.id.rl_choice)
        val toggle_choices_visibility = findViewById<ImageView>(R.id.toggle_choices_visibility)

        var isShowingAnswers = true

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
                txt_B.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
                txt_C.setBackground(getResources().getDrawable(R.drawable.card_background_answer, null))
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
                txt_C.setBackground(getResources().getDrawable(R.drawable.card_background_answer, null))
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
                txt_B.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
                txt_C.setBackground(getResources().getDrawable(R.drawable.card_background_error, null))
            }
        })

        rl_choice.setOnClickListener(View.OnClickListener {
            txt_A.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
            txt_B.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
            txt_C.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
        })

        toggle_choices_visibility.setOnClickListener(View.OnClickListener {
            if(isShowingAnswers==true)
            {
                isShowingAnswers = false
                toggle_choices_visibility.setImageResource(R.drawable.eye_off_lined)
                rl_choice.visibility = View.INVISIBLE
            }
            else
            {
                isShowingAnswers = true
                toggle_choices_visibility.setImageResource(R.drawable.eye_lined)
                rl_choice.visibility = View.VISIBLE
            }
        })
    }
}