package com.esmaeel.networkretrycalladapterlibrary

import com.esmaeel.statelib.currentActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.io.IOException

class  CallDefault<R>():Call<R>{
    override fun clone(): Call<R> {
        TODO("Not yet implemented")
    }

    override fun execute(): Response<R> {
        TODO("Not yet implemented")
    }

    override fun enqueue(callback: Callback<R>) {
        TODO("Not yet implemented")
    }

    override fun isExecuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun isCanceled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun request(): Request {
        TODO("Not yet implemented")
    }

    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }

}


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
        makeRequest(delegate, callback, onNetworkError)
    }

    lateinit var repeatedCall: Call<R>
    lateinit var repeatedCallback: Callback<R>
    var repeatedOnNetworkError: onNetworkError = null

    private fun retryLastCall() {
        this.onNetworkError?.let {
            makeRequest(this.repeatedCall, this.repeatedCallback, repeatedOnNetworkError)
        }
    }


    // separate function to be able to call it self again of retry.
    fun makeRequest(call: Call<R>, callback: Callback<R>, onNetworkError: onNetworkError) {
        GlobalScope.launch(Dispatchers.IO) {
            try {

                val response = call.awaitResponse()

                if (response.isSuccessful)
                    callback.onResponse(this@NetworkRetryCall, Response.success(response.body()))
                else
                    callback.onFailure(
                        this@NetworkRetryCall,
                        Throwable(response.errorBody()?.string())
                    )

            } catch (e: Exception) {
                when (e) {

                    is IOException -> {
                        // show dialog and wait for user response
                        // then return the success response.
                        /* Network Error (WIFI for example) */


                        // cache the last failed network call to be able to repeat again from outside
                        this@NetworkRetryCall.repeatedCall = this@NetworkRetryCall.clone()
                        this@NetworkRetryCall.repeatedCallback = callback
                        this@NetworkRetryCall.repeatedOnNetworkError = onNetworkError

                        // invoke if provided else run this block
                        GlobalScope.launch(Dispatchers.Main) {
                            onNetworkError?.invoke(
                                this@NetworkRetryCall.clone(),
                                e
                            ) {

                                // on retry logic finished this gets invoked
                                retryLastCall()
                            }
                        }
                            ?: run {
                                currentActivity?.showNetworkDialog(
                                    error = e.message ?: "",
                                    onRetry = {
                                        retryLastCall()
                                    })
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


    /**
     *  modifying clone function to wrap the default call with our call.
     */
    override fun clone(): Call<R> = NetworkRetryCall(delegate.clone())

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