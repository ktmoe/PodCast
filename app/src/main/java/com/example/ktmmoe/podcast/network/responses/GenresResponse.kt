package com.example.ktmmoe.podcast.network.responses

import com.example.ktmmoe.podcast.data.vos.Genre
import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres") val genres: List<Genre>? = listOf()
)