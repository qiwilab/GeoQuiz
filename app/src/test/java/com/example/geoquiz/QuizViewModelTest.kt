package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import org.junit.Test
//import org.junit.jupiter.api.Assertions.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

class QuizViewModelTest {
    @Test
    fun providesExpectedQuestionText() {
        val savedStateHandle = SavedStateHandle()
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun wrapsAroundQuestionBank() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 5))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertEquals(R.string.question_asia, quizViewModel.currentQuestionText)
        quizViewModel.moveToNext()
        assertEquals(R.string.question_australia, quizViewModel.currentQuestionText)
    }

    @Test
    fun checkingCurrentQuestionAnswerBehaviour() {
        val savedStateHandle = SavedStateHandle(mapOf(CURRENT_INDEX_KEY to 0))
        val quizViewModel = QuizViewModel(savedStateHandle)
        assertTrue(quizViewModel.currentQuestionAnswer)
        quizViewModel.moveToNext()
        assertTrue(quizViewModel.currentQuestionAnswer)
        quizViewModel.moveToNext()
        assertFalse(quizViewModel.currentQuestionAnswer)
        quizViewModel.moveToNext()
        assertFalse(quizViewModel.currentQuestionAnswer)
    }
}