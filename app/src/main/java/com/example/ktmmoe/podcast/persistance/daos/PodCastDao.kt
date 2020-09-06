package com.example.ktmmoe.podcast.persistance.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.ktmmoe.podcast.data.vos.PodCast
import com.example.ktmmoe.podcast.network.responses.PodCastResponse

/**
 * Created by ktmmoe on 04, September, 2020
 **/
@Dao
interface PodCastDao {

    @Query("SELECT * FROM podcast")
    fun getPodCasts(): LiveData<List<PodCastResponse>>

    @Query("DELETE FROM podcast")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPodCast(podCast: PodCastResponse)
}