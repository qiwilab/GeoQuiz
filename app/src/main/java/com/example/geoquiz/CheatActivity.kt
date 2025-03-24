package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityCheatBinding

const val EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.example.geoquiz.answer_is_true"
private const val KEY_WAS_CHEATED = "com.example.geoquiz.key_was_cheated"
private const val EXTRA_CHEATED_QUESTION_COUNTER = "com.example.geoquiz.cheated_question_counter"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false
    private var wasCheated = false
    private var cheatedQuestionCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        wasCheated = savedInstanceState?.getBoolean(KEY_WAS_CHEATED, false) ?: false

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        cheatedQuestionCounter = 3 - intent.getIntExtra(EXTRA_CHEATED_QUESTION_COUNTER, 0)

        setAnswerShowResult(wasCheated)

        with(binding) {
            var buttonText = getString(R.string.show_answer_button, cheatedQuestionCounter)
            showAnswerButton.setText(buttonText)
            if (cheatedQuestionCounter == 0) {
                showAnswerButton.isEnabled = false
            }
            showAnswerButton.setOnClickListener {
                val answerText = when {
                    answerIsTrue -> R.string.true_button
                    else -> R.string.false_button
                }
                binding.answerTextView.setText(answerText)
                wasCheated = true
                setAnswerShowResult(wasCheated)
            }

            apiLevelVersion.setText("API Level ${Build.VERSION.SDK_INT}")
        }
    }

    private fun setAnswerShowResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(KEY_WAS_CHEATED, wasCheated)
    }

    companion object {
        fun newIntent(packageContext: Context,
                      answerIsTrue: Boolean, cheatedQuestionCounter: Int): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_CHEATED_QUESTION_COUNTER, cheatedQuestionCounter)
            }
        }
    }
}