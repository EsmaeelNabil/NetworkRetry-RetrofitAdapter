package com.esmaeel.networkretrycalladapter.data

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.library.BuildConfig
import com.esmaeel.networkretrycalladapterlibrary.NetworkRetryCallAdapterFactory
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceLocator private constructor() {

    private fun getNetworkRetryCallAdapter(): NetworkRetryCallAdapterFactory {
        return NetworkRetryCallAdapterFactory.create { call, exception, retryCall ->

            Toast.makeText(activity.applicationContext, exception.message, Toast.LENGTH_SHORT)
                .show()

            val dialog = AlertDialog.Builder(activity)
            dialog.setMessage(exception.message)
                .setPositiveButton(
                    "Try again!"
                ) { dialog, which ->
                    retryCall.invoke()
                }.show()
        }
    }

    private fun getRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkRetryCallAdapterFactory.create())
            .client(client)
            .build()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

    private fun getHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
            .build()
    }

    companion object {
        private var manager: ServiceLocator? = null
        private lateinit var activity: Activity

        fun getApiService(activity: Activity): ApiService {
            this.activity = activity
            if (manager == null) {
                manager = ServiceLocator()
            }

            return manager!!
                .getRetrofit(
                    manager!!.getHttpClient(),
                    Gson()
                )
                .create(ApiService::class.java)
        }
    }
}