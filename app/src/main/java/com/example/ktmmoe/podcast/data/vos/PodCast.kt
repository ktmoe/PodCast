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