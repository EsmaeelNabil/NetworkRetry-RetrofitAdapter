package com.esmaeel.networkretrycalladapterlibrary

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import java.io.IOException

/**
 * R is the the response type
 * delegate is the actual call 'the default retrofit call'
 */
class NetworkRetryCall<R>(
    private val delegate: Call<R>,
    private val onNetworkError: onNetworkError = null
) : Call<R> {

    // our custom implementation for the retry callback
    override fun enqueue(callback: Callback<R>) {
        makeRequest(delegate, callback)
    }

    // separate function to be able to call it self again of retry.
    private fun makeRequest(call: Call<R>, callback: Callback<R>) {
        GlobalScope.launch(Dispatchers.IO) {
            try {

                val response = call.awaitResponse()

                if (response.isSuccessful)
                    callback.onResponse(this@NetworkRetryCall, Response.success(response.body()))
                else
                    callback.onFailure(this@NetworkRetryCall, HttpException(response))

            } catch (e: Exception) {
                when (e) {

                    /* Network Error (WIFI for example) */
                    is IOException -> {

                        // invoke callback in provided and wait for retry invocation.
                        // if onNetworkError Callback is not provided callback.OnFailure will be called instead.

                        GlobalScope.launch(Dispatchers.Main) {
                            // invoke if provided
                            onNetworkError?.invoke(this@NetworkRetryCall.clone(), e) {
                                // on retry logic finished this gets invoked
                                retryLastCall(callback)
                            } //else run this block
                                ?: run {
                                    // return a failure
                                    callback.onFailure(this@NetworkRetryCall, e)
                                }
                        }

                    }

                    else -> {
                        /* Unknown Error throwable onFailure is called with a different throwable */
                        callback.onFailure(this@NetworkRetryCall, e)
                    }
                }
            }
        }
    }

    private fun retryLastCall(callback: Callback<R>) {
        makeRequest(
            this@NetworkRetryCall.clone(),
            callback
        )
    }


    /**
     *  modifying clone function to wrap the default call with our call.
     */
    override fun clone(): Call<R> = NetworkRetryCall(delegate.clone(), onNetworkError)

    /*
    * default retrofit call behaviour
    */
    override fun execute(): Response<R> = delegate.execute()
    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun cancel() = delegate.cancel()
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()

}