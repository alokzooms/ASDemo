package com.maxlabs.asdemo.data.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maxlabs.asdemo.model.Category

class CategoryListConverter {
    @TypeConverter
    fun fromString(value: String): List<Category> {
        val listType = object : TypeToken<List<Category>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Category>): String {
        return Gson().toJson(list)
    }
}
