package com.example.flashcardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val btCancel = findViewById<ImageView>(R.id.bt_cancel)
        val btSave = findViewById<ImageView>(R.id.bt_save)

        btCancel.setOnClickListener(View.OnClickListener {
            finish()
        })

        btSave.setOnClickListener(View.OnClickListener {
            val question = findViewById<EditText>(R.id.etquestion)
            val answer = findViewById<EditText>(R.id.etanswer)
            val data = Intent()

            data.putExtra("question",question.text.toString())
            data.putExtra("answer",answer.text.toString())

            setResult(RESULT_OK,data)

            finish()
        })
    }
}