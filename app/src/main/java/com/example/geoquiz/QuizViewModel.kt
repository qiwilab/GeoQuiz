package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val CHEATED_COUNTER = "CHEATED_COUNTER"


class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    var cheatedQuestion: Boolean
        get() = questionBank[currentIndex].isCheated
        set(value) { questionBank[currentIndex].isCheated = value }

    var cheatedQuestionCounter: Int
        get() = savedStateHandle.get(CHEATED_COUNTER) ?: 0
        set(value) = savedStateHandle.set(CHEATED_COUNTER, value)

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
}