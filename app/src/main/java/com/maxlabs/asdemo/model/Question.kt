package com.maxlabs.asdemo.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.maxlabs.asdemo.data.db.converters.AnswerChoiceListConverter
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "question_table")
@TypeConverters(AnswerChoiceListConverter::class)
data class Question(
    @PrimaryKey(autoGenerate = false) var id: Int,
    var name: String,
    var selectedAnswerChoiceId: Int,
    val answerChoices: List<AnswerChoice>
): Parcelable