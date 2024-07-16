package com.maxlabs.asdemo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.maxlabs.asdemo.model.Question

@Dao
interface QuestionDao {
    @Insert
    suspend fun insert(question: Question)

    @Query("SELECT * FROM question_table")
    suspend fun getAllQuestions(): List<Question>

    @Query("UPDATE question_table SET selectedAnswerChoiceId = :selectedAnswerId WHERE id = :questionId")
    suspend fun updateSelectedAnswerChoice(questionId: Int, selectedAnswerId: Int): Int

    @Query("SELECT * FROM question_table WHERE id = :questionId")
    suspend fun getQuestionById(questionId: Int): Question?

    @Update
    suspend fun updateQuestion(question: Question)
}
