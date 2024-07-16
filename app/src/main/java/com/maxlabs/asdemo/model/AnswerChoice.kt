package com.maxlabs.asdemo.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "answer_choice_table")
data class AnswerChoice(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val score: Float
): Parcelable