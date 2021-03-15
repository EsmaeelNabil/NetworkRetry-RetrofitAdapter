package com.pixiedia.pixicommerce.command

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.Type

class NetworkRetryCallAdapterFactory : CallAdapter.Factory() {

    companion object {
        fun create(): NetworkRetryCallAdapterFactory = NetworkRetryCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return NetworkRetryCallAdapter(retrofit.nextCallAdapter(this, returnType, annotations))
    }

}