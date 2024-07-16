package com.maxlabs.asdemo.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maxlabs.asdemo.model.AnswerChoice

class AnswerChoiceListConverter {
    @TypeConverter
    fun fromString(value: String): List<AnswerChoice> {
        val listType = object : TypeToken<List<AnswerChoice>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<AnswerChoice>): String {
        return Gson().toJson(list)
    }
}
