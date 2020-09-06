package com.example.ktmmoe.podcast.data.models

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.example.ktmmoe.podcast.data.vos.Genre
import com.example.ktmmoe.podcast.data.vos.PodCast
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.network.responses.GenresResponse
import com.example.ktmmoe.podcast.network.responses.PlayListResponse
import com.example.ktmmoe.podcast.network.responses.PodCastResponse
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers

/**
 * Created by ktmmoe on 04, September, 2020
 **/
object PodCastModelImpl : PodCastModel, BaseModel() {
    override fun getRandomPodCast(onError: (String) -> Unit): LiveData<List<PodCastResponse>> {
        return mTheDB.podCastDao().getPodCasts()
    }

    override fun getGenres(onError: (String) -> Unit): LiveData<List<Genre>> {
        return mTheDB.genreDao().getGenres()
    }

    override fun getPlayList(onError: (String) -> Unit): LiveData<List<PodCastWrapper>> {
        return mTheDB.playListDao().getFullPlaylist()
    }

    @SuppressLint("CheckResult")
    override fun getAllDataAndSaveToDB(onSuccess: () -> Unit, onError: (String) -> Unit) {
        Observable.zip(
            mPodCastApi.getRandomPodCast(),
            mPodCastApi.getPlayList(),
            mPodCastApi.getGenres(),
            Function3<PodCastResponse, PlayListResponse, GenresResponse, Zipper> { a, b, c ->
                return@Function3 Zipper(a, b, c)
            }).subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe ({
                clearDB()
                mTheDB.podCastDao().insertPodCast(it.randomPodCast)
                mTheDB.playListDao().insertFullPlaylist(it.playListResponse.items?: emptyList())
                mTheDB.genreDao().insertGenres(it.genresResponse.genres?: emptyList())
            },{
                onError.invoke(it.localizedMessage?:"No Internet Connection.")
            })
    }

    private fun clearDB() {
        mTheDB.podCastDao().deleteAll()
        mTheDB.genreDao().deleteAll()
        mTheDB.playListDao().deleteAll()
    }
}


data class Zipper(
    val randomPodCast: PodCastResponse,
    val playListResponse: PlayListResponse,
    val genresResponse: GenresResponse
)