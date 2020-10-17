package com.example.ktmmoe.podcast.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.ktmmoe.podcast.network.responses.PodCastResponse
import com.example.ktmmoe.podcast.persistance.typeconverters.PodCastResponseTypeConverter
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "podCastWrapper")
@TypeConverters(PodCastResponseTypeConverter::class)
data class PodCastWrapper(
    @SerializedName("added_at_ms") val addedAtMs: Long? = 0L,
    @SerializedName("data") val `data`: PodCastResponse = PodCastResponse(),
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") val id: Int = 0,
    @SerializedName("notes") val notes: String? = "",
    @SerializedName("type") val type: String? = ""
) : Serializable