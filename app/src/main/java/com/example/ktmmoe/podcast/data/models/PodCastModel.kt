package com.example.ktmmoe.podcast.data.models

import androidx.lifecycle.LiveData
import com.example.ktmmoe.podcast.data.vos.Download
import com.example.ktmmoe.podcast.data.vos.Genre
import com.example.ktmmoe.podcast.data.vos.PodCast
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.network.responses.GenresResponse
import com.example.ktmmoe.podcast.network.responses.PlayListResponse
import com.example.ktmmoe.podcast.network.responses.PodCastResponse

/**
 * Created by ktmmoe on 04, September, 2020
 **/
interface PodCastModel {
    fun getRandomPodCast(onError: (String) -> Unit) : LiveData<List<PodCastResponse>>

    fun getGenres(onError: (String) -> Unit) : LiveData<List<Genre>>

    fun getPlayList(onError: (String) -> Unit) : LiveData<List<PodCastWrapper>>

    fun saveDownload(podCast: PodCastWrapper, onError: (String) -> Unit)

    fun getDownloads(onError: (String) -> Unit) : LiveData<List<Download>>

    fun getAllDataAndSaveToDB(onSuccess: () -> Unit, onError: (String) -> Unit)
}