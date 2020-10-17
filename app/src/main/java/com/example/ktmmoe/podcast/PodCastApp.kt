package com.example.ktmmoe.podcast

import android.app.Application
import androidx.work.*
import com.example.ktmmoe.podcast.data.models.FireStorePodCastModelImpl
import com.example.ktmmoe.podcast.workers.PrefetchDataWorker
import java.util.concurrent.TimeUnit

/**
 * Created by ktmmoe on 05, September, 2020
 **/
class PodCastApp : Application() {
    override fun onCreate() {
        super.onCreate()

//        RealtimeDBPodCastModelImpl.initDatabase(applicationContext)
        FireStorePodCastModelImpl.initDatabase(applicationContext)

//        getDataOneTime()
//        getNewsPeriodically()
//        getNewsWhileInDozeMode()
    }

    private fun getDataOneTime(){
        val getEventsWorkRequest = OneTimeWorkRequest
            .Builder(PrefetchDataWorker::class.java)
            .build()
        WorkManager.getInstance(applicationContext)
            .enqueue(getEventsWorkRequest)
    }

    private fun getDataPeriodically(){
        val constraints = Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val getEventsPeriodicallyWorkRequest = PeriodicWorkRequest
            .Builder(PrefetchDataWorker::class.java,30, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(getEventsPeriodicallyWorkRequest)
    }

    private fun getDataWhileInDozeMode(){
        val constraints = Constraints
            .Builder()
            .setRequiresDeviceIdle(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val getEventsWhileInDozeModeWorkRequest = PeriodicWorkRequest
            .Builder(PrefetchDataWorker::class.java, 1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(getEventsWhileInDozeModeWorkRequest)
    }
}