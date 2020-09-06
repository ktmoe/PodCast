package com.example.ktmmoe.podcast.workers

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Created by ktmmoe on 05, September, 2020
 **/
class DownloadTask {
    private val executor: Executor =
        Executors.newSingleThreadExecutor()
    private val handler: Handler = Handler(Looper.getMainLooper())

    interface Callback<R> {
        fun onComplete(result: R)
    }

    fun <R> executeAsync(callable: Callable<R>, callback: Callback<R>) {
        executor.execute {
            val result: R = callable.call()
            handler.post { callback.onComplete(result) }
        }
    }
}