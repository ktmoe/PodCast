package com.example.ktmmoe.podcast.persistance.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ktmmoe.podcast.data.vos.PodCast
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper

/**
 * Created by ktmmoe on 04, September, 2020
 **/
@Dao
interface PlayListDao {
    @Query("SELECT * FROM podCastWrapper")
    fun getFullPlaylist(): LiveData<List<PodCastWrapper>>

    @Query("DELETE FROM podCastWrapper")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFullPlaylist(podCastList: List<PodCastWrapper>)
}