package com.example.ktmmoe.podcast.persistance.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by ktmmoe on 04, September, 2020
 **/
class StringListTypeConverter {
    @TypeConverter
    fun toString(stringList: ArrayList<String>): String {
        return Gson().toJson(stringList)
    }

    @TypeConverter
    fun toCommentList(commentListJsonStr: String): ArrayList<String> {
        val stringListType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(commentListJsonStr, stringListType)
    }
}