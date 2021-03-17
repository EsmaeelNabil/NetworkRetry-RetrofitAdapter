package com.esmaeel.networkretry.data

import androidx.databinding.library.BuildConfig
import com.esmaeel.networkretry.DialogManager
import com.esmaeel.networkretrycalladapterlibrary.NetworkRetryCallAdapterFactory
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class ServiceLocator @Inject constructor(private val dialogManager: DialogManager) {

    private fun getNetworkRetryCallAdapter(): NetworkRetryCallAdapterFactory {
        return NetworkRetryCallAdapterFactory.create { call, exception, retryCall ->

            dialogManager.showNetworkScreen(
                message = exception.message ?: "",
                onRetry = { retryCall() }
            )

        }
    }

    private fun getRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(getNetworkRetryCallAdapter())
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

    fun apiService() = getRetrofit(getHttpClient(), Gson()).create(ApiService::class.java)

}