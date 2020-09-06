package com.example.ktmmoe.podcast.persistance.typeconverters

import androidx.room.TypeConverter
import com.example.ktmmoe.podcast.network.responses.PodCastResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by ktmmoe on 04, September, 2020
 **/
class PodCastResponseTypeConverter {
    @TypeConverter
    fun toString(podCastResponse: PodCastResponse): String {
        return Gson().toJson(podCastResponse)
    }

    @TypeConverter
    fun toPodCastResponse(json: String): PodCastResponse {
        val podCastResponseType = object : TypeToken<PodCastResponse>() {}.type
        return Gson().fromJson(json, podCastResponseType)
    }
}