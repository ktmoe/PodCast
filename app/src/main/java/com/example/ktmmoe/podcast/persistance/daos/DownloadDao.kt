package com.example.ktmmoe.podcast.persistance.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ktmmoe.podcast.data.vos.Download
import com.example.ktmmoe.podcast.data.vos.Genre
import com.example.ktmmoe.podcast.data.vos.PodCast

/**
 * Created by ktmmoe on 04, September, 2020
 **/
@Dao
interface DownloadDao {

    @Query("SELECT * FROM download")
    fun getDownloads(): LiveData<List<Download>>

    @Query("DELETE FROM download")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDownload(download: Download): Long
}