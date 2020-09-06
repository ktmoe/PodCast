package com.example.ktmmoe.podcast.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.ktmmoe.podcast.data.models.PodCastModel
import com.example.ktmmoe.podcast.data.models.PodCastModelImpl

/**
 * Created by ktmmoe on 05, September, 2020
 **/
abstract class BaseWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    val mPodCastModel : PodCastModel = PodCastModelImpl
}