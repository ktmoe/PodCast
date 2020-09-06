package com.example.ktmmoe.podcast.network.responses

import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.google.gson.annotations.SerializedName

data class PlayListResponse(
    @SerializedName("items") val items: List<PodCastWrapper>? = emptyList()
)