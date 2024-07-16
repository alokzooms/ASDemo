package com.maxlabs.asdemo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.maxlabs.asdemo.data.db.converters.QuestionListConverter
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "category_table")
@TypeConverters(QuestionListConverter::class)
data class Category(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val questions: List<Question>
): Parcelable