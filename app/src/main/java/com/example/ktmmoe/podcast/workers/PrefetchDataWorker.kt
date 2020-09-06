package com.example.ktmmoe.podcast.workers

import android.content.Context
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import androidx.work.WorkerParameters

/**
 * Created by ktmmoe on 05, September, 2020
 **/
class PrefetchDataWorker(context: Context, workerParams: WorkerParameters) :
    BaseWorker(context, workerParams) {

    override fun doWork(): Result {
        var result = failure()

        mPodCastModel.getAllDataAndSaveToDB(
            onSuccess = {
                result = success()
            },
            onError = {
                result = failure()
            }
        )
        return result
    }
}