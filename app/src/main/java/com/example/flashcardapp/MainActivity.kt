package com.example.flashcardapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var flashcardDatabase: FlashcardDatabase
    var currentCardDisplayedIndex = 0
    var index = 0
    var id = 0
    var cardToEdit: Flashcard? = null
    var allFlashcards = mutableListOf<Flashcard>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flashcard_question = findViewById<TextView>(R.id.flashcard_question)
        val flashcard_answer = findViewById<TextView>(R.id.flashcard_answer)
        val rl_question_answer = findViewById<RelativeLayout>(R.id.rl_question_answer)
        val iv_empty_card = findViewById<ImageView>(R.id.iv_empty_card)

        val rl_choice = findViewById<RelativeLayout>(R.id.rl_choice)

        val txt_A = findViewById<TextView>(R.id.txt_A)
        val txt_B = findViewById<TextView>(R.id.txt_B)
        val txt_C = findViewById<TextView>(R.id.txt_C)

        val btAdd = findViewById<ImageView>(R.id.bt_add)
        val btEdit = findViewById<ImageView>(R.id.bt_edit)

        flashcardDatabase = FlashcardDatabase(this)
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

        if (allFlashcards.size > 0) {
            index = getRandomNumber(0, allFlashcards.size - 1)

            while (index == currentCardDisplayedIndex) {
                index = getRandomNumber(0, allFlashcards.size - 1)
            }

            currentCardDisplayedIndex = index

            flashcard_question.text = allFlashcards[currentCardDisplayedIndex].question
            flashcard_answer.text = allFlashcards[currentCardDisplayedIndex].answer
            txt_A.text = allFlashcards[currentCardDisplayedIndex].wrongAnswer1
            txt_B.text = allFlashcards[currentCardDisplayedIndex].answer
            txt_C.text = allFlashcards[currentCardDisplayedIndex].wrongAnswer2


            iv_empty_card.visibility = View.INVISIBLE
            rl_question_answer.visibility = View.VISIBLE
            rl_choice.visibility = View.VISIBLE
        }

        findViewById<View>(R.id.btn_next).setOnClickListener {
            // don't try to go to next card if you have no cards to begin with
            if (allFlashcards.size == 0) {
                // return here, so that the rest of the code in this onClickListener doesn't execute
                return@setOnClickListener
            }

            index = getRandomNumber(0, allFlashcards.size - 1)

            while (index == currentCardDisplayedIndex) {
                index = getRandomNumber(0, allFlashcards.size - 1)
            }

            // advance our pointer index so we can show the next card
            currentCardDisplayedIndex = index

            // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
            if (currentCardDisplayedIndex >= allFlashcards.size) {
                Snackbar.make(
                    flashcard_question, // This should be the TextView for displaying your flashcard question
                    "You've reached the end of the cards, going back to start.",
                    Snackbar.LENGTH_LONG
                )
                    .show()
                currentCardDisplayedIndex = 0
            }


            val leftOutAnim = AnimationUtils.loadAnimation(this, R.anim.left_out)
            val rightInAnim = AnimationUtils.loadAnimation(this, R.anim.right_in)

            leftOutAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    // this method is called when the animation first starts
                }

                override fun onAnimationEnd(animation: Animation?) {
                    // this method is called when the animation is finished playing
                }

                override fun onAnimationRepeat(animation: Animation?) {
                    // we don't need to worry about this method
                }
            })


            // set the question and answer TextViews with data from the database
            allFlashcards = flashcardDatabase.getAllCards().toMutableList()
            val (question, answer, wrong_answer_1, wrong_answer_2) = allFlashcards[currentCardDisplayedIndex]

            flashcard_answer.text = answer
            flashcard_question.text = question
            txt_A.text = wrong_answer_1
            txt_B.text = answer
            txt_C.text = wrong_answer_2


            flashcard_question.visibility = View.VISIBLE
            flashcard_answer.visibility = View.INVISIBLE


            flashcard_question.startAnimation(leftOutAnim)
            flashcard_question.startAnimation(rightInAnim)



            txt_A.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
            txt_B.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
            txt_C.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
        }

        findViewById<View>(R.id.btn_delete).setOnClickListener {
            // don't try to go to next card if you have no cards to begin with
            if (allFlashcards.size == 0) {
                // return here, so that the rest of the code in this onClickListener doesn't execute
                return@setOnClickListener
            }


            val flashcardQuestionToDelete = flashcard_question.text.toString()
            flashcardDatabase.deleteCard(flashcardQuestionToDelete)
            allFlashcards = flashcardDatabase.getAllCards().toMutableList()
            currentCardDisplayedIndex--

            if (allFlashcards.size == 0) {
                iv_empty_card.visibility = View.VISIBLE
                rl_question_answer.visibility = View.INVISIBLE
                flashcard_question.visibility = View.VISIBLE
                flashcard_answer.visibility = View.INVISIBLE
                rl_choice.visibility = View.INVISIBLE
            } else {
                if (currentCardDisplayedIndex == -1)
                    currentCardDisplayedIndex = 0

                val (question, answer, wrong_answer_1, wrong_answer_2) = allFlashcards[currentCardDisplayedIndex]
                flashcard_answer.text = answer
                flashcard_question.text = question
                txt_A.text = wrong_answer_1
                txt_B.text = answer
                txt_C.text = wrong_answer_2

                rl_question_answer.visibility = View.VISIBLE
                flashcard_question.visibility = View.VISIBLE
                flashcard_answer.visibility = View.INVISIBLE
                rl_choice.visibility = View.VISIBLE

                txt_A.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
                txt_B.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
                txt_C.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
            }

        }

        flashcard_question.setOnClickListener(View.OnClickListener {
            // get the center for the clipping circle
            val cx = flashcard_answer.width / 2
            val cy = flashcard_answer.height / 2

            // get the final radius for the clipping circle
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

            // create the animator for this view (the start radius is zero)
            val anim =
                ViewAnimationUtils.createCircularReveal(flashcard_answer, cx, cy, 0f, finalRadius)
            flashcard_question.visibility = View.INVISIBLE
            flashcard_answer.visibility = View.VISIBLE

            anim.duration = 500
            anim.start()
        })

        flashcard_answer.setOnClickListener(View.OnClickListener {
            flashcard_question.visibility = View.VISIBLE
            flashcard_answer.visibility = View.INVISIBLE
        })

        txt_A.setOnClickListener(View.OnClickListener {
            if (txt_A.text == flashcard_answer.text) {
                txt_A.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_answer,
                        null
                    )
                )
                txt_B.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
                txt_C.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
            } else {
                txt_A.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_error,
                        null
                    )
                )
                txt_B.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_answer,
                        null
                    )
                )
                txt_C.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
            }
        })

        txt_B.setOnClickListener(View.OnClickListener {
            if (txt_B.text == flashcard_answer.text) {
                txt_A.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
                txt_B.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_answer,
                        null
                    )
                )
                txt_C.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
            } else {
                txt_A.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
                txt_B.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_error,
                        null
                    )
                )
                txt_C.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
            }
        })

        txt_C.setOnClickListener(View.OnClickListener {
            if (txt_C.text == flashcard_answer.text) {
                txt_A.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
                txt_B.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
                txt_C.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_answer,
                        null
                    )
                )
            } else {
                txt_A.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_choice,
                        null
                    )
                )
                txt_B.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_answer,
                        null
                    )
                )
                txt_C.setBackground(
                    getResources().getDrawable(
                        R.drawable.card_background_error,
                        null
                    )
                )
            }
        })

        rl_choice.setOnClickListener(View.OnClickListener {
            txt_A.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
            txt_B.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
            txt_C.setBackground(getResources().getDrawable(R.drawable.card_background_choice, null))
        })

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data: Intent? = result.data

                if (data != null) {
                    val question = data.getStringExtra("question")
                    val answer = data.getStringExtra("answer")
                    val option1 = data.getStringExtra("option1")
                    val option2 = data.getStringExtra("option2")

                    if (question != null && answer != null) {

                        flashcardDatabase.insertCard(
                            Flashcard(
                                question.toString(),
                                answer.toString(),
                                option1.toString(),
                                option2.toString()
                            )
                        )
                        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

                        if (allFlashcards.size > 0) {
                            flashcard_question.text = question
                            flashcard_answer.text = answer
                            txt_A.text = option1
                            txt_B.text = answer
                            txt_C.text = option2

                            iv_empty_card.visibility = View.INVISIBLE
                            rl_question_answer.visibility = View.VISIBLE
                            rl_choice.visibility = View.VISIBLE

                            txt_A.setBackground(
                                getResources().getDrawable(
                                    R.drawable.card_background_choice,
                                    null
                                )
                            )
                            txt_B.setBackground(
                                getResources().getDrawable(
                                    R.drawable.card_background_choice,
                                    null
                                )
                            )
                            txt_C.setBackground(
                                getResources().getDrawable(
                                    R.drawable.card_background_choice,
                                    null
                                )
                            )
                        }

                        Snackbar.make(
                            findViewById(R.id.flashcard_question),
                            "Card successfylly created",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Log.e(
                            "TAG",
                            "Missing question or answer to input into database. Question is $question and answer is $answer"
                        )
                    }
                } else {
                    Log.i("TAG", "Returned null data from AddCardActivity ")
                }
            }

        val editResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data: Intent? = result.data

                if (data != null) {
                    val question = data.getStringExtra("question")
                    val answer = data.getStringExtra("answer")
                    val option1 = data.getStringExtra("option1")
                    val option2 = data.getStringExtra("option2")

                    if (question != null && answer != null) {

                        cardToEdit = Flashcard(question, answer, option1, option2, id)

                        flashcardDatabase.updateCard(cardToEdit!!)

                        flashcard_question.text = question
                        flashcard_answer.text = answer
                        txt_A.text = option1
                        txt_B.text = answer
                        txt_C.text = option2

                        txt_A.setBackground(
                            getResources().getDrawable(
                                R.drawable.card_background_choice,
                                null
                            )
                        )
                        txt_B.setBackground(
                            getResources().getDrawable(
                                R.drawable.card_background_choice,
                                null
                            )
                        )
                        txt_C.setBackground(
                            getResources().getDrawable(
                                R.drawable.card_background_choice,
                                null
                            )
                        )

                        Snackbar.make(
                            findViewById(R.id.flashcard_question),
                            "Card successfylly created",
                            Snackbar.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        Log.e(
                            "TAG",
                            "Missing question or answer to input into database. Question is $question and answer is $answer"
                        )
                    }
                } else {
                    Log.i("TAG", "Returned null data from AddCardActivity ")
                }
            }

        btAdd.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            resultLauncher.launch(intent)
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
        })

        btEdit.setOnClickListener(View.OnClickListener {

            allFlashcards = flashcardDatabase.getAllCards().toMutableList()

            cardToEdit = allFlashcards[currentCardDisplayedIndex]

            id = cardToEdit?.uuid!!

            val intent = Intent(this, AddCardActivity::class.java)

            intent.putExtra("question", cardToEdit?.question)
            intent.putExtra("answer", cardToEdit?.answer)
            intent.putExtra("option1", cardToEdit?.wrongAnswer1)
            intent.putExtra("option2", cardToEdit?.wrongAnswer2)

            editResultLauncher.launch(intent)
        })
    }

    fun getRandomNumber(minNumber: Int, maxNumber: Int): Int {
        return (minNumber..maxNumber).random() // generated random from 0 to 10 included
    }
}