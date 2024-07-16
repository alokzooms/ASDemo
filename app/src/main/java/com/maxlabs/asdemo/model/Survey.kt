package com.maxlabs.asdemo.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.maxlabs.asdemo.data.db.converters.CategoryListConverter

@Entity(tableName = "survey_table")
@TypeConverters(CategoryListConverter::class)
data class Survey(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val categories: List<Category>
)