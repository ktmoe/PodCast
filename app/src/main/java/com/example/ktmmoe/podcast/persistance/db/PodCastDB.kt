package com.example.ktmmoe.podcast.persistance.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ktmmoe.podcast.data.vos.Download
import com.example.ktmmoe.podcast.data.vos.Genre
import com.example.ktmmoe.podcast.data.vos.PodCast
import com.example.ktmmoe.podcast.data.vos.PodCastWrapper
import com.example.ktmmoe.podcast.network.responses.PodCastResponse
import com.example.ktmmoe.podcast.persistance.daos.GenreDao
import com.example.ktmmoe.podcast.persistance.daos.PlayListDao
import com.example.ktmmoe.podcast.persistance.daos.PodCastDao

/**
 * Created by ktmmoe on 04, September, 2020
 **/
@Database(entities = [PodCastResponse::class, Genre::class, PodCastWrapper::class, Download::class], version = 1, exportSchema = false)
abstract class PodCastDB : RoomDatabase() {
    companion object {
        val DB_NAME = "PodCast.DB"
        var dbInstance: PodCastDB? = null

        fun getDBInstance(context: Context): PodCastDB {
            when (dbInstance) {
                null -> {
                    dbInstance = Room.databaseBuilder(context, PodCastDB::class.java,
                        DB_NAME
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            val i =
                dbInstance
            return i!!
        }
    }

    abstract fun podCastDao(): PodCastDao
    abstract fun genreDao() : GenreDao
    abstract fun playListDao(): PlayListDao
}