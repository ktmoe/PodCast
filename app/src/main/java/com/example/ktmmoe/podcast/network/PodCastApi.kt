package com.example.ktmmoe.podcast.network

import com.example.ktmmoe.podcast.network.responses.GenresResponse
import com.example.ktmmoe.podcast.network.responses.PlayListResponse
import com.example.ktmmoe.podcast.network.responses.PodCastResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by ktmmoe on 26, September, 2020
 **/
interface PodCastApi {
    @GET("just_listen")
    fun getRandomPodCast(): Observable<PodCastResponse>

    @GET("genres")
    fun getGenres(@Query("top_level_only")  topLevelOnly: Int = 0) : Observable<GenresResponse>

    @GET("playlists/{id}")
    fun getPlayList(
        @Path("id") id: String = "m1pe7z60bsw",
        @Query("type") type: String = "episode_list",
        @Query("last_timestamp_ms") lastTimeStampMs: Int = 0,
        @Query("sort") sort: String = "recent_added_first"
    ) : Observable<PlayListResponse>
}