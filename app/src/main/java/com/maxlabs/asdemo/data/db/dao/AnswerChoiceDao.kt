package com.maxlabs.asdemo.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.maxlabs.asdemo.model.AnswerChoice

@Dao
interface AnswerChoiceDao {
    @Insert
    suspend fun insert(answerChoice: AnswerChoice)

    @Query("SELECT * FROM answer_choice_table")
    suspend fun getAllAnswerChoices(): List<AnswerChoice>
}
