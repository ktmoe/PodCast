package com.example.ktmmoe.podcast.data.models

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ktmmoe.podcast.data.vos.*
import com.example.ktmmoe.podcast.network.responses.PodCastResponse
import com.example.ktmmoe.podcast.network.responses.podCastResponse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

/**
 * Created by ktmmoe on 04, September, 2020
 **/
@SuppressLint("CheckResult")
object FireStorePodCastModelImpl : PodCastModel, BaseModel() {
    private val database = Firebase.firestore
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    private val randomPodCastLiveData: MutableLiveData<List<PodCastResponse>> = MutableLiveData()
    private val genresLiveData: MutableLiveData<List<Genre>> = MutableLiveData()
    private val playListLiveData: MutableLiveData<List<PodCastWrapper>> = MutableLiveData()

    override fun getRandomPodCast(onError: (String) -> Unit): LiveData<List<PodCastResponse>> {
        database.collection("latest_episodes")
            .addSnapshotListener { value, error ->
                error?.let {
                    onError(it.localizedMessage ?: "No Internet Connection.")
                } ?: run {
                    val list = mutableListOf<PodCastResponse>()
                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val data = document.data!!
                        val podCastResponse = data.podCastResponse()
                        list.add(podCastResponse)
                    }
                    randomPodCastLiveData.postValue(list)
                }
            }

        return randomPodCastLiveData
    }

    override fun getGenres(onError: (String) -> Unit): LiveData<List<Genre>> {
        val genres: MutableList<Genre> = mutableListOf()
        database.collection("genres")
            .addSnapshotListener { value, error ->
                error?.let {
                    onError(it.localizedMessage ?: "No Internet Connection.")
                } ?: run {
                    val result = value?.documents ?: arrayListOf()

                    for (document in result) {
                        val data = document.data!!
                        val genre = data.genre()
                        genres.add(genre)
                    }
                    genresLiveData.postValue(genres)
                }
            }
        return genresLiveData
    }

    override fun getPlayList(onError: (String) -> Unit): LiveData<List<PodCastWrapper>> {
        database.collection("latest_episodes")
            .addSnapshotListener { value, error ->
                error?.let {
                    onError(it.localizedMessage ?: "No Internet Connection.")
                } ?: run {
                    val list = mutableListOf<PodCastWrapper>()
                    val result = value?.documents ?: arrayListOf()
                    result.forEachIndexed { index, document ->
                        val data = document.data!!
                        val podCastResponse = data.podCastResponse()
                        list.add(PodCastWrapper(id = index, data = podCastResponse))
                    }

                    playListLiveData.postValue(list)
                }
            }

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