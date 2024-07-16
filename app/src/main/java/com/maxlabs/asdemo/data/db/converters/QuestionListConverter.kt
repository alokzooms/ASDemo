package com.maxlabs.asdemo.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maxlabs.asdemo.model.Question

class QuestionListConverter {
    @TypeConverter
    fun fromString(value: String): List<Question> {
        val listType = object : TypeToken<List<Question>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Question>): String {
        return Gson().toJson(list)
    }
}
