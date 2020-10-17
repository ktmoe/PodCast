package com.example.ktmmoe.podcast.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "genre")
data class Genre(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("parent_id") val parentId: Int = 0
)

fun Map<String, Any>.genre(): Genre= Genre(id = (this["id"] as Long).toInt(), name = this["name"] as String)