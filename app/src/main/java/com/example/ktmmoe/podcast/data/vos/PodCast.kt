package com.example.ktmmoe.podcast.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PodCast(
    @SerializedName("id") val id: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("listennotes_url") val listenNotesUrl: String = "",
    @SerializedName("publisher") val publisher: String = "",
    @SerializedName("thumbnail") val thumbnail: String = "",
    @SerializedName("title") val title: String = ""
): Serializable

fun Map<String, Any>.podCast(): PodCast = PodCast(id = this["id"] as String, image = this["image"] as String, listenNotesUrl = this["listennotes_url"] as String, publisher = this["publisher"] as String, thumbnail = this["thumbnail"] as String, title = this["title"] as String)