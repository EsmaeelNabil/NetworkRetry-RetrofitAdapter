package com.pixiedia.pixicommerce.command

import com.esmaeel.networkretrycalladapterlibrary.showNetworkDialog
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


/**
 * R is the the response type
 * delegate is the actual call 'the default retrofit call'
 */
internal class NetworkRetryCall<R>(private val delegate: Call<R>) : Call<R> {

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
                        currentActivity?.showNetworkDialog(
                            error = e.message ?: "",
                            onRetry = {
                                makeRequest(call.clone(), callback)
                            })
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