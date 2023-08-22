package com.example.flashcardapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val question = intent.getStringExtra("question");
        val answer = intent.getStringExtra("answer");
        val option1 = intent.getStringExtra("option1");
        val option2 = intent.getStringExtra("option2");

        val etquestion = findViewById<TextView>(R.id.etquestion)
        val etanswer = findViewById<TextView>(R.id.etanswer)
        val etoption1 = findViewById<TextView>(R.id.etoption1)
        val etoption2 = findViewById<TextView>(R.id.etoption2)

        val btCancel = findViewById<ImageView>(R.id.bt_cancel)
        val btSave = findViewById<ImageView>(R.id.bt_save)


        etquestion.text = question
        etanswer.text = answer
        etoption1.text = option1
        etoption2.text = option2

        btCancel.setOnClickListener(View.OnClickListener {
            finish()
        })

        btSave.setOnClickListener(View.OnClickListener {

            if (!etquestion.text.isNullOrEmpty() && !etanswer.text.isNullOrEmpty() && !etoption1.text.isNullOrEmpty() && !etoption2.text.isNullOrEmpty()) {
                val data = Intent()

                data.putExtra("question", etquestion.text.toString())
                data.putExtra("answer", etanswer.text.toString())
                data.putExtra("option1", etoption1.text.toString())
                data.putExtra("option2", etoption2.text.toString())

                setResult(RESULT_OK, data)

                finish()
            } else {
                Toast.makeText(this, "Must enter both Question and Answer", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }
}