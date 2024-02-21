package com.juanroig.composecourse.data.datasource.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ListIntConverter() {
    @TypeConverter
    fun listToString(value: List<Int>): String? =
        Gson().toJson(value)

    @TypeConverter
    fun stringToListInt(json: String?): List<Int>? {
        val listType: Type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson<List<Int>>(json, listType)
    }
}
