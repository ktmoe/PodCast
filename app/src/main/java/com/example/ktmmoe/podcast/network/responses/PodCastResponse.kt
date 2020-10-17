package com.example.ktmmoe.podcast.network.responses

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.ktmmoe.podcast.data.vos.PodCast
import com.example.ktmmoe.podcast.data.vos.podCast
import com.example.ktmmoe.podcast.persistance.typeconverters.PodCastTypeConverter
import com.google.firebase.database.IgnoreExtraProperties
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "podcast")
@TypeConverters(PodCastTypeConverter::class)
@IgnoreExtraProperties
data class PodCastResponse(
    @SerializedName("audio") val audio: String = "",
    @SerializedName("audio_length_sec") val audio_length_sec: Int? = 0,
    @SerializedName("description") val description: String = "",
    @SerializedName("explicit_content") val explicitContent: Boolean? = false,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") val id: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("link") val link: String? = "",
    @SerializedName("listennotes_edit_url") val listenNotesEditUrl: String? = "",
    @SerializedName("listennotes_url") val listenNotesUrl: String? = "",
    @SerializedName("maybe_audio_invalid") val maybeAudioInvalid: Boolean? = false,
    @SerializedName("podcast") val podCast: PodCast? = PodCast(),
    @SerializedName("pub_date_ms") val pubDateMs: Long? = 0L,
    @SerializedName("thumbnail") val thumbnail: String? = "",
    @SerializedName("title") val title: String = ""
): Serializable

fun Map<String, Any>.podCastResponse (): PodCastResponse {
    val mPodCast = (this["podcast"] as Map<String, Any>).podCast()
    return PodCastResponse(audio = this["audio"] as String, audio_length_sec = (this["audio_length_sec"] as Long).toInt(),
        description = this["description"] as String, explicitContent = this["explicit_content"] as Boolean,
        id= this["id"] as String, image = this["image"] as String, link = this["link"] as String,
        listenNotesEditUrl = this["listennotes_edit_url"] as String, listenNotesUrl = this["listennotes_url"] as String,
        maybeAudioInvalid = this["maybe_audio_invalid"] as Boolean, podCast = mPodCast,
    pubDateMs = this["pub_date_ms"] as Long, thumbnail = this["thumbnail"] as String, title = this["title"] as String)
}