package com.esmaeel.networkretrycalladapterlibrary

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

/**
 * Created by Esmaeel Nabil on Mar, 2021
 */


internal class NetworkRetryCallAdapter<R, T>(
    private val delegated: CallAdapter<R, T>
) : CallAdapter<R, T> {

    override fun adapt(call: Call<R>): T = delegated.adapt(NetworkRetryCall(call))

    override fun responseType(): Type = delegated.responseType()
}


