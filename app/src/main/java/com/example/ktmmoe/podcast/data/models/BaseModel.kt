package com.example.ktmmoe.podcast.data.models

import android.content.Context
import com.example.ktmmoe.podcast.network.PodCastApi
import com.example.ktmmoe.podcast.persistance.db.PodCastDB
import com.example.ktmmoe.podcast.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by ktmmoe on 04, September, 2020
 **/


abstract class BaseModel {

    protected var mPodCastApi: PodCastApi
    protected lateinit var mTheDB: PodCastDB

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        mPodCastApi = retrofit.create(PodCastApi::class.java)
    }

    fun initDatabase(context: Context) {
        mTheDB = PodCastDB.getDBInstance(context)
    }
}