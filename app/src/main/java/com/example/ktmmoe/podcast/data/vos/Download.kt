package com.example.ktmmoe.podcast.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
/**
 * Created by ktmmoe on 06, September, 2020
 **/
@Entity(tableName = "download")
class Download {
    @PrimaryKey (autoGenerate = false)
    @SerializedName("id") val id : Int = 0
    @SerializedName("podCast") val podCast: PodCastWrapper = PodCastWrapper()
}