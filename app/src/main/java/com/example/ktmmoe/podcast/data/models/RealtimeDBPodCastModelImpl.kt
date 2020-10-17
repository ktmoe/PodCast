package com.example.ktmmoe.podcast.data.models

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ktmmoe.podcast.data.vos.Download
import com.example.ktmmoe.podcast.data.vos.Genre
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.network.responses.PodCastResponse
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Exception

/**
 * Created by ktmmoe on 04, September, 2020
 **/
@SuppressLint("CheckResult")
object RealtimeDBPodCastModelImpl : PodCastModel, BaseModel() {

    private val database: DatabaseReference = Firebase.database.reference

    private val randomPodCastLiveData: MutableLiveData<List<PodCastResponse>> = MutableLiveData()
    private val genresLiveData: MutableLiveData<List<Genre>> = MutableLiveData()
    private val playListLiveData: MutableLiveData<List<PodCastWrapper>> = MutableLiveData()

    override fun getRandomPodCast(onError: (String) -> Unit): LiveData<List<PodCastResponse>> {
        database.child("latest_episodes").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                onError(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    it.getValue(PodCastResponse::class.java)?.let {response ->
                        randomPodCastLiveData.postValue(listOf(response))
                    }
                }
            }
        })
        return randomPodCastLiveData
    }

    override fun getGenres(onError: (String) -> Unit): LiveData<List<Genre>> {
        val genres: MutableList<Genre> = mutableListOf()
        database.child("genres").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                throw Exception(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {data->
                    data.getValue(Genre::class.java)?.let {
                        genres.add(it)
                    }
                }
                genresLiveData.postValue(genres)
            }
        })
        return genresLiveData
    }

    override fun getPlayList(onError: (String) -> Unit): LiveData<List<PodCastWrapper>> {
        val podCasts: MutableList<PodCastWrapper> = mutableListOf()
        database.child("latest_episodes").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                throw Exception(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEachIndexed { index, data->
                    data.getValue(PodCastResponse::class.java)?.let {
                        podCasts.add(PodCastWrapper(id = index, data = it))
                    }
                }
                playListLiveData.postValue(podCasts)
            }
        })
        return playListLiveData
    }

    override fun saveDownload(podCast: PodCastWrapper, onError: (String) -> Unit) {
        val a = mTheDB.downloadDao().insertDownload(Download(podCast.id, podCast))
    }

    override fun getDownloads(onError: (String) -> Unit): LiveData<List<Download>> {
        return mTheDB.downloadDao().getDownloads()
    }

    override fun getAllDataAndSaveToDB(onSuccess: () -> Unit, onError: (String) -> Unit) {
        //we don't do this here
    }
}