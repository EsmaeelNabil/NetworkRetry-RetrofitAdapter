package com.esmaeel.networkretrycalladapter.data

import androidx.databinding.library.BuildConfig
import com.google.gson.Gson
import com.pixiedia.pixicommerce.command.NetworkRetryCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceLocator private constructor() {

    private fun getNetworkRetryCallAdapter(): NetworkRetryCallAdapterFactory {
        return NetworkRetryCallAdapterFactory.create()
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

    companion object {
        private var manager: ServiceLocator? = null

        fun getApiService(): ApiService {

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