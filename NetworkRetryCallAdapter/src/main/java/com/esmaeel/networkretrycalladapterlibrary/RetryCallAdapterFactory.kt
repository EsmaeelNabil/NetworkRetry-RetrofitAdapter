package com.esmaeel.networkretrycalladapterlibrary

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

typealias onNetworkError = ((Call<*>, Throwable, onRetryCall: () -> Unit) -> Unit)?

class NetworkRetryCallAdapterFactory(private val onNetworkError: onNetworkError = null) :
    CallAdapter.Factory() {

    companion object {
        fun create(onNetworkError: onNetworkError = null): NetworkRetryCallAdapterFactory =
            NetworkRetryCallAdapterFactory(onNetworkError)
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return NetworkRetryCallAdapter(
            retrofit.nextCallAdapter(this, returnType, annotations),
            onNetworkError
        )
    }

}