package com.maxlabs.asdemo.util

import com.maxlabs.asdemo.model.Category

class UtilMethods {

    /**
     * Verify if the survey is completed or not
     */
    fun verifyCompletionPercentage(categories: List<Category>?): Boolean {
        var totalQuestions = 0
        var totalAnsweredQuestions = 0
        categories?.forEach { category ->
            totalQuestions += category.questions.size
            totalAnsweredQuestions += category.questions.count { it.selectedAnswerChoiceId != 0 }
        }
        return totalQuestions == totalAnsweredQuestions
    }
}