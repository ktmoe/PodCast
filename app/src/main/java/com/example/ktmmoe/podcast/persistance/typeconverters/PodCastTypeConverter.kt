package com.example.ktmmoe.podcast.persistance.typeconverters

import androidx.room.TypeConverter
import com.example.ktmmoe.podcast.data.vos.PodCast
import com.example.ktmmoe.podcast.network.responses.PodCastResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by ktmmoe on 04, September, 2020
 **/
class PodCastTypeConverter {
    @TypeConverter
    fun toString(podCast: PodCast): String {
        return Gson().toJson(podCast)
    }

    @TypeConverter
    fun toPodCast(json: String): PodCast {
        val podCastType = object : TypeToken<PodCast>() {}.type
        return Gson().fromJson(json, podCastType)
    }
}