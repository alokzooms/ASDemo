package com.maxlabs.asdemo.util

import com.maxlabs.asdemo.model.Category
import com.maxlabs.asdemo.model.Question
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class UtilMethodsTest {

    var utilMethods = UtilMethods()

    @Test
    fun testAllQuestionsAnswered() {
        val categories = listOf(
            Category(
                id = 1,
                name = "Category 1",
                questions = listOf(
                    Question(id = 1, name = "Question 1", selectedAnswerChoiceId = 1, emptyList()),
                    Question(id = 2, name = "Question 2", selectedAnswerChoiceId = 2, emptyList())
                )
            ),
            Category(
                id = 2,
                name = "Category 2",
                questions = listOf(
                    Question(id = 3, name = "Question 3", selectedAnswerChoiceId = 1, emptyList()),
                    Question(id = 4, name = "Question 4", selectedAnswerChoiceId = 2, emptyList())
                )
            )
        )
        assertTrue(utilMethods.verifyCompletionPercentage(categories))
    }

    @Test
    fun testNotAllQuestionsAnswered() {
        val question1 = mock<Question> {
            on { selectedAnswerChoiceId } doReturn 0
        }
        val question2 = mock<Question> {
            on { selectedAnswerChoiceId } doReturn 2
        }
        val category1 = mock<Category> {
            on { questions } doReturn listOf(question1, question2)
        }
        val category2 = mock<Category> {
            on { questions } doReturn listOf(question1, question2)
        }
        val categories = listOf(category1, category2)
        assertFalse(utilMethods.verifyCompletionPercentage(categories))
    }

}