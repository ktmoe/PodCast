package com.example.ktmmoe.podcast.persistance.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ktmmoe.podcast.data.vos.Genre
import com.example.ktmmoe.podcast.data.vos.PodCast

/**
 * Created by ktmmoe on 04, September, 2020
 **/
@Dao
interface GenreDao {

    @Query("SELECT * FROM genre")
    fun getGenres(): LiveData<List<Genre>>

    @Query("DELETE FROM genre")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<Genre>)
}